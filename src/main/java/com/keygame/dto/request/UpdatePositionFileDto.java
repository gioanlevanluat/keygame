package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePositionFileDto {
    @NotNull
    private Long fileId;
    @NotNull
    @Min(0)
    private Integer position;
}
