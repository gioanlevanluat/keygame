package com.keygame.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSimpleDto {
    private Long id;
    private String name;
    private String handle;
    private String image;
    private BigDecimal price;
    private BigDecimal comparePrice;
    private Integer platform;
    private Integer brandId;
    private String brandName;
    private String brandImage;
    private String regionId;
    private String regionName;
    private String regionImage;
    private Float discount;
}
