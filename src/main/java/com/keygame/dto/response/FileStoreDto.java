package com.keygame.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileStoreDto {
    private Long id;
    private String name;
    private Integer type;
    private Long productId;
    private Integer position;
}
