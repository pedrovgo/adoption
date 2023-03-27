package com.pet.adoption.mapper;

import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.po.DataLoader;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class)
public interface DataLoaderMapper {
    DataLoaderResponse map(DataLoader dataLoader);
}
