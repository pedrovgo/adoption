package com.pet.adoption.services;

import com.pet.adoption.AdoptionApplication;
import com.pet.adoption.api.v1.request.AnimalSearchRequest;
import com.pet.adoption.api.v1.response.DataLoaderResponse;
import com.pet.adoption.enums.Status;
import com.pet.adoption.events.DataLoaderEvent;
import com.pet.adoption.po.Animal;
import com.pet.adoption.repository.DataLoaderRepository;
import com.pet.adoption.service.AdoptionService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AdoptionApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
@EnableAsync
public class AdoptionServiceImplTest {

    @Autowired
    private AdoptionService adoptionService;

    @Autowired
    private DataLoaderRepository dataLoaderRepository;

    @Test
    public void integrationTest() throws InterruptedException {
        final DataLoaderResponse dataLoaderResponse = adoptionService.dataLoader();

        Assertions.assertNotEquals(dataLoaderRepository.count(), 0);
        Assertions.assertNotNull(dataLoaderResponse.getId());
        Assertions.assertNotNull(dataLoaderResponse.getCreatedAt());
        Assertions.assertTrue(dataLoaderResponse.getCreatedAt().isBefore(LocalDateTime.now()));
        Assertions.assertFalse(dataLoaderResponse.getFinished());

        Awaitility.await().atMost(5, TimeUnit.MINUTES).until(() -> adoptionService.getDataLoader(dataLoaderResponse.getId()).getFinished());

        final String id = UUID.randomUUID().toString();
        final LocalDateTime now = LocalDateTime.now();
        final AnimalSearchRequest animalSearchRequest = new AnimalSearchRequest();
        animalSearchRequest.setStatus(Status.Disponivel);

        Assertions.assertEquals(adoptionService.search(animalSearchRequest).getTotal(), 0);

        adoptionService.handleDataLoader(new DataLoaderEvent(this, Arrays.asList(
            Animal.builder()
                .id(id)
                .name("Animal")
                .description("Desc")
                .createdAt(now)
                .updatedAt(now)
                .status(Status.Disponivel)
                .build())));

        Assertions.assertNotEquals(adoptionService.search(animalSearchRequest).getTotal(), 0);

        adoptionService.changeStatus(id, Status.Adotado);

        Assertions.assertEquals(adoptionService.search(animalSearchRequest).getTotal(), 0);
    }
}
