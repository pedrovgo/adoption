package com.pet.adoption.service.client;

import com.pet.adoption.service.client.response.Image;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.util.List;

@FeignClient(
        name = "data-loader-client",
        url = "https://"
)
public interface DataLoaderClient {
    @GetMapping()
    ResponseEntity<List<Image>> getImages(
            @RequestHeader(name="x-api-key") String apiKey,
            URI uri
    );
}
