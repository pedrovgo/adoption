package com.pet.adoption.api.v1.request;

import com.pet.adoption.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnimalSearchRequest extends SearchRequest{
    private String search;
    private String category;
    private Status status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAtStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAtEnd;
}
