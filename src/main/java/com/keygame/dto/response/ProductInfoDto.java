package com.keygame.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private String handle;
    private String image;
    private String groupCode;
    private String description;
    private String requirement;
    private BigDecimal price;
    private BigDecimal comparePrice;
    private Long platform;
    private String platFormName;
    private Long regionId;
    private String releaseDate;
    private String developer;
    private String publisher;
    private String supportedLanguages;
    private List<FileStoreDto> files;
    private List<Long> collectionIds;
    private Long dealEnd;
    private String note;
}
