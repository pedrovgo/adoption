package com.pet.adoption.service.impl;

import com.pet.adoption.service.DataLoaderClientService;
import com.pet.adoption.service.client.DataLoaderClient;
import com.pet.adoption.service.client.response.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class DataLoaderClientServiceImpl implements DataLoaderClientService {

    private final DataLoaderClient dataLoaderClient;
    private final Integer limit;

    @Autowired
    public DataLoaderClientServiceImpl(final DataLoaderClient dataLoaderClient,
                                       @Value("${data-loader.limit}") final Integer limit) {
        this.dataLoaderClient = dataLoaderClient;
        this.limit = limit;
    }

    @Override
    public List<Image> search(final String url, final Integer page, String apiKey) {
        final URI uri = URI.create(buildUrl(url, page));
        final ResponseEntity<List<Image>> response = dataLoaderClient.getImages(apiKey, uri);
        return response.getBody();
    }

    private String buildUrl(final String url, final Integer page) {
        return new StringBuilder(url)
                .append("?limit=").append(limit)
                .append("&page=").append(page)
                .append("&has_breeds=1")
                .append("&order=ASC")
                .toString();
    }
}
