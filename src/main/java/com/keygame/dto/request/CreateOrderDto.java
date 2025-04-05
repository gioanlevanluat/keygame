package com.keygame.dto.request;

import com.keygame.dto.CartDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDto {

    @NotNull
    private CartDto cartDto;

    @NotBlank
    private String sendEmail;
}
