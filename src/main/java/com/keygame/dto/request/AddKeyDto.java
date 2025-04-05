package com.keygame.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddKeyDto {
    @NotEmpty
    private List<String> codes;
    @NotNull
    private Long productId;
    private String supplier;
    @NotNull
    private BigDecimal basePrice;
}
