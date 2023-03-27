package com.pet.adoption.api.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pet.adoption.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalResponse {
    private String id;
    private String name;
    private String description;
    private String image;
    private String category;
    private LocalDateTime createdAt;
    private Status status;
}
