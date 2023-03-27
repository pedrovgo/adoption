package com.pet.adoption.service;

import com.pet.adoption.api.v1.request.AnimalSearchRequest;
import com.pet.adoption.api.v1.response.AnimalResponse;
import com.pet.adoption.api.v1.response.AnimalSearchResponse;
import com.pet.adoption.enums.Status;
import com.pet.adoption.po.Animal;

import java.util.List;

public interface AnimalService {
    AnimalSearchResponse search(AnimalSearchRequest animalSearchRequest);
    void saveFromDataLoader(List<Animal> animalList);
    AnimalResponse changeStatus(final String id, final Status status);
}
