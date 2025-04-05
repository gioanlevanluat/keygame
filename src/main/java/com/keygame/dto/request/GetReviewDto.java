package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GetReviewDto {
    private Integer star;
    @NotNull
    private Long productId;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
