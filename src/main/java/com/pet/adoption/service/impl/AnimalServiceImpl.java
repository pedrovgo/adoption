package com.pet.adoption.service.impl;

import com.pet.adoption.api.v1.request.AnimalSearchRequest;
import com.pet.adoption.api.v1.response.AnimalResponse;
import com.pet.adoption.api.v1.response.AnimalSearchResponse;
import com.pet.adoption.enums.Status;
import com.pet.adoption.exceptions.NotFoundException;
import com.pet.adoption.mapper.AnimalMapper;
import com.pet.adoption.po.Animal;
import com.pet.adoption.repository.AnimalRepository;
import com.pet.adoption.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalMapper animalMapper;
    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(
            final AnimalMapper animalMapper,
            final AnimalRepository animalRepository){
        this.animalMapper = animalMapper;
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalSearchResponse search(AnimalSearchRequest animalSearchRequest) {
        final PageRequest pageRequest = PageRequest.of(animalSearchRequest.getPage(),animalSearchRequest.getLimit());
        final Page<Animal> animalPage = animalRepository.search(
                animalSearchRequest.getSearch(), animalSearchRequest.getCategory(),
                Objects.isNull(animalSearchRequest.getStatus()) ? null : animalSearchRequest.getStatus().name(),
                animalSearchRequest.getCreatedAtStart(), animalSearchRequest.getCreatedAtEnd(),
                pageRequest
        );
        return buildSearchResponse(animalSearchRequest, animalPage);
    }

    @Override
    public void saveFromDataLoader(List<Animal> animalList) {
        final Set<String> ids = animalList.stream().map(Animal::getId).collect(Collectors.toSet());
        final List<Animal> animalListFromDb = animalRepository.findAllById(ids);
        animalList.removeIf(animal -> animalListFromDb.parallelStream().anyMatch(animalFromDb ->
                        animal.getId().equals(animalFromDb.getId()) && animalFromDb.getStatus().equals(Status.Adotado)
                )
        );

        if(!animalList.isEmpty()) {
            final LocalDateTime now = LocalDateTime.now();
            animalList.parallelStream().forEach(animal -> {
                final Optional<Animal> animalOptional = animalListFromDb.parallelStream().filter(animalFromDb -> animalFromDb.getId().equals(animal.getId())).findFirst();

                if(animalOptional.isPresent()) {
                    final Animal animalFromDb = animalOptional.get();
                    animal.setStatus(animalFromDb.getStatus());
                    animal.setCreatedAt(animalFromDb.getCreatedAt());
                } else {
                    animal.setStatus(Status.Disponivel);
                    animal.setCreatedAt(now);
                }

                animal.setUpdatedAt(now);
            });
            animalRepository.saveAll(animalList);
        }
    }

    @Override
    public AnimalResponse changeStatus(String id, Status status) {
        Animal animal = animalRepository.findById(id).orElseThrow(NotFoundException::new);
        animal.setStatus(status);
        animal = animalRepository.save(animal);
        return animalMapper.map(animal);
    }

    private AnimalSearchResponse buildSearchResponse(
            final AnimalSearchRequest animalSearchRequest,
            final Page<Animal> animalPage) {
        final AnimalSearchResponse response = new AnimalSearchResponse(animalMapper.map(animalPage.getContent()));
        response.setLimit(animalSearchRequest.getLimit());
        response.setPage(animalSearchRequest.getPage());
        response.setTotal(animalPage.getTotalElements());
        response.setTotalPages(animalPage.getTotalPages());
        return response;
    }

}
