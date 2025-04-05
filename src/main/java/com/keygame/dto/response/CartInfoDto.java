package com.keygame.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keygame.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartInfoDto {
    private BigDecimal subPrice;
    private BigDecimal paymentFee;
    private BigDecimal totalPrice;
    @JsonIgnore
    private List<Product> products;
}
