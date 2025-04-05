package com.keygame.dto.request;

import com.keygame.common.SortEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchProductDto {
    private String name;
    private List<Integer> platforms;
    private List<Integer> brandIds;
    private List<String> regionIds;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
    private String collectionCode;
    private List<Long> collectionIds;
    private SortEnum.Product sortEnum;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
}
