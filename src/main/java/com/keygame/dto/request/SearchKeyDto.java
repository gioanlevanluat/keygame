package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchKeyDto {
    private List<Long> productIds;
    private Boolean isOrdered;
    private Integer pageSize;
    private Integer pageNumber;
}
