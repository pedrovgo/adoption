package com.pet.adoption.api.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataLoaderResponse {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean finished;
    private String errorMessage;
}
