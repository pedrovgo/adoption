package com.pet.adoption.api.v1.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private Integer page = 0;
    private Integer limit = 10;
}
