package com.pet.adoption.service;

import com.pet.adoption.api.v1.request.AnimalSearchRequest;
import com.pet.adoption.api.v1.response.AnimalResponse;
import com.pet.adoption.api.v1.response.AnimalSearchResponse;
import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.enums.Status;
import com.pet.adoption.events.DataLoaderEvent;

public interface AdoptionService {
    DataLoaderResponse dataLoader();
    DataLoaderResponse getDataLoader(String id);

    void handleDataLoader(DataLoaderEvent event);

    AnimalSearchResponse search(AnimalSearchRequest animalSearchRequest);

    AnimalResponse changeStatus(final String id, final Status status);
}
