package com.keygame.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CollectionInfo {
    private Long id;
    private String name;
    private String code;
    private String description;
    private List<ProductSimpleDto> products;
}
