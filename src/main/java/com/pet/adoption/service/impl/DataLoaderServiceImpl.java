package com.pet.adoption.service.impl;

import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.events.DataLoaderEvent;
import com.pet.adoption.exceptions.NotFoundException;
import com.pet.adoption.mapper.DataLoaderMapper;
import com.pet.adoption.po.Animal;
import com.pet.adoption.po.DataLoader;
import com.pet.adoption.repository.DataLoaderRepository;
import com.pet.adoption.service.DataLoaderClientService;
import com.pet.adoption.service.DataLoaderService;
import com.pet.adoption.service.client.response.Breed;
import com.pet.adoption.service.client.response.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

    private final List<String> categories;
    private final List<String> urls;
    private final List<String> apiKeys;
    private final DataLoaderMapper dataLoaderMapper;
    private final DataLoaderClientService dataLoaderClientService;
    private final DataLoaderRepository dataLoaderRepository;

    private final ApplicationEventMulticaster applicationEventMulticaster;

    public DataLoaderServiceImpl(
            @Value("${data-loader.categories}") final List<String> categories,
            @Value("${data-loader.urls}") final List<String> urls,
            @Value("${data-loader.api-keys}") final List<String> apiKeys,
            final DataLoaderMapper dataLoaderMapper,
            final DataLoaderClientService dataLoaderClientService,
            final DataLoaderRepository dataLoaderRepository,
            final ApplicationEventMulticaster applicationEventMulticaster
    ) {
        this.categories = categories;
        this.urls = urls;
        this.apiKeys = apiKeys;
        this.dataLoaderMapper = dataLoaderMapper;
        this.dataLoaderClientService = dataLoaderClientService;
        this.dataLoaderRepository = dataLoaderRepository;
        this.applicationEventMulticaster = applicationEventMulticaster;
    }

    @Override
    public DataLoaderResponse dataLoader(){
        final Optional<DataLoader> dataLoaderOptional = dataLoaderRepository.findByFinishedFalse();

        if(dataLoaderOptional.isPresent()) {
            return dataLoaderMapper.map(dataLoaderOptional.get());
        }

        final DataLoader dataLoader = createDataLoader();
        startDataLoader(dataLoader);
        return dataLoaderMapper.map(dataLoader);
    }

    @Override
    public DataLoaderResponse getDataLoader(String id) {
        final DataLoader dataLoader = dataLoaderRepository.findById(id).orElseThrow(NotFoundException::new);
        return dataLoaderMapper.map(dataLoader);
    }

    private DataLoader createDataLoader() {
        final LocalDateTime now = LocalDateTime.now();
        return dataLoaderRepository.save(DataLoader.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(now)
                .updatedAt(now)
                .finished(false)
                .build());
    }

    private void finishDataLoader(final DataLoader dataLoader) {
        dataLoader.setFinished(true);
        dataLoader.setUpdatedAt(LocalDateTime.now());
        dataLoaderRepository.save(dataLoader);
    }

    public void startDataLoader(final DataLoader dataLoader) {
        CompletableFuture.runAsync(() -> {
            try {
                final List<Animal> animalList = new ArrayList();

                categories.parallelStream().forEach(category -> {
                    final Integer index = categories.indexOf(category);
                    final String url = urls.get(index);
                    final String apiKey = apiKeys.get(index);
                    Integer page = 0;
                    List<Image> images = dataLoaderClientService.search(url, page, apiKey);

                    while(!images.isEmpty()) {

                        images.forEach(image -> {
                            if(Objects.nonNull(image.getBreeds()) && !image.getBreeds().isEmpty()) {
                                final Breed breed = image.getBreeds().get(0);
                                animalList.add(
                                    Animal.builder()
                                        .id(image.getId())
                                        .name(breed.getName())
                                        .description(Optional.ofNullable(breed.getDescription()).orElse(breed.getBredFor()))
                                        .image(image.getUrl())
                                        .category(category)
                                        .build());
                            }
                        });

                        page++;
                        images = dataLoaderClientService.search(url, page, apiKey);

                        dataLoader.setUpdatedAt(LocalDateTime.now());
                        dataLoaderRepository.save(dataLoader);
                    }
                });

                applicationEventMulticaster.multicastEvent(new DataLoaderEvent(this, animalList));
                finishDataLoader(dataLoader);
            } catch (Exception e) {
                dataLoader.setErrorMessage(e.getMessage());
                finishDataLoader(dataLoader);
            }
        });
    }
}
