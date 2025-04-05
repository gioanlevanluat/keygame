package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SearchProductInCollectionDto {
    @NotBlank
    private String collection;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
