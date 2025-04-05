package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String handle;
    @NotNull
    private String image;
    private String groupCode;
    @NotBlank
    private String description;
    private String requirement;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal comparePrice;
    @NotNull
    private Integer platform;
    @NotNull
    private Long regionId;
    private String releaseDate;
    private String developer;
    private String publisher;
    private String supportedLanguages;
    private List<Long> files;
    private Long dealEnd;
    private String note;
    private Boolean autoSendKey = false;
}
