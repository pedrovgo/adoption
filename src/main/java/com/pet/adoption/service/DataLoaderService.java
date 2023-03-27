package com.pet.adoption.service;

import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.po.Animal;
import com.pet.adoption.po.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DataLoaderService {
    DataLoaderResponse dataLoader();
    DataLoaderResponse getDataLoader(String id);
}
