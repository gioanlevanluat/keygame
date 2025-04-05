package com.keygame.dto.request;

import com.keygame.common.SortEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuickSearchProductDto {
    private String name;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
    private String collectionCode;
    private List<Long> collectionIds;
    private SortEnum.Product sortProductEnum;
}
