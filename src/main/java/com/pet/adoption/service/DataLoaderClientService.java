package com.pet.adoption.service;

import com.pet.adoption.service.client.response.Image;

import java.util.List;

public interface DataLoaderClientService {
    List<Image> search(final String url, final Integer page, String apiKey);
}
