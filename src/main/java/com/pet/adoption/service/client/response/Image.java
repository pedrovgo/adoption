package com.pet.adoption.service.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private String id;
    private String url;
    private List<Breed> breeds;
}
