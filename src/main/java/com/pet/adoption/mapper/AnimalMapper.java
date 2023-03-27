package com.pet.adoption.mapper;

import com.pet.adoption.api.v1.response.AnimalResponse;
import com.pet.adoption.po.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MappingConfig.class)
public interface AnimalMapper {
    @Mapping(source = "id", target = "id")
    AnimalResponse map(Animal animal);
    List<AnimalResponse> map(List<Animal> animalList);
}
