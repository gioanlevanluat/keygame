package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MovePositionFileDto {
    @NotNull
    private Long fileId1;
    @NotNull
    private Long fileId2;
}
