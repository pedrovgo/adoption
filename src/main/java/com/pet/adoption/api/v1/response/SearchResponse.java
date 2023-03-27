package com.pet.adoption.api.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private Integer page;
    private Integer limit;
    private Long total;
    private Integer totalPages;
}
