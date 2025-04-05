package com.keygame.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class RegistryDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    private boolean isSubscriber;
}
