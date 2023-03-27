package com.pet.adoption.api.v1;

import com.pet.adoption.api.v1.request.AnimalSearchRequest;
import com.pet.adoption.api.v1.response.AnimalResponse;
import com.pet.adoption.api.v1.response.AnimalSearchResponse;
import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.enums.Status;
import com.pet.adoption.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @Autowired
    public AdoptionController(final AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping("/search")
    public ResponseEntity<AnimalSearchResponse> search(AnimalSearchRequest request) {
        return ResponseEntity.ok(adoptionService.search(request));
    }

    @PostMapping("/change-status/{id}/status/{status}")
    public ResponseEntity<AnimalResponse> changeStatus(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "status") Status status
    ) {
        return ResponseEntity.ok(adoptionService.changeStatus(id, status));
    }

    @GetMapping("/data-loader/{id}")
    public ResponseEntity<DataLoaderResponse> getDataLoader(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(adoptionService.getDataLoader(id));
    }

    @PostMapping("/data-loader")
    public ResponseEntity dataLoader() {
        return ResponseEntity.accepted().body(adoptionService.dataLoader());
    }

}
