package com.pet.adoption.service.impl;

import com.pet.adoption.api.v1.request.AnimalSearchRequest;
import com.pet.adoption.api.v1.response.AnimalResponse;
import com.pet.adoption.api.v1.response.AnimalSearchResponse;
import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.enums.Status;
import com.pet.adoption.events.DataLoaderEvent;
import com.pet.adoption.service.AdoptionService;
import com.pet.adoption.service.AnimalService;
import com.pet.adoption.service.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    final AnimalService animalService;
    final DataLoaderService dataLoaderService;

    @Autowired
    public AdoptionServiceImpl(
            final AnimalService animalService,
            final DataLoaderService dataLoaderService){
        this.animalService = animalService;
        this.dataLoaderService = dataLoaderService;
    }

    @Override
    public DataLoaderResponse dataLoader() {
        return dataLoaderService.dataLoader();
    }

    @Override
    public DataLoaderResponse getDataLoader(String id) {
        return dataLoaderService.getDataLoader(id);
    }

    @Override
    public AnimalSearchResponse search(AnimalSearchRequest animalSearchRequest) {
        return animalService.search(animalSearchRequest);
    }

    @Override
    public AnimalResponse changeStatus(String id, Status status) {
        return animalService.changeStatus(id, status);
    }

    @Override
    @EventListener(DataLoaderEvent.class)
    public void handleDataLoader(DataLoaderEvent event) {
        animalService.saveFromDataLoader(event.getAnimalList());
    }
}
