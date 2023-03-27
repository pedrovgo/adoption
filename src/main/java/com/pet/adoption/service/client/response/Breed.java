package com.pet.adoption.service.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Breed {
    private String name;
    private String description;
    @JsonProperty("bred_for")
    private String bredFor;
}
