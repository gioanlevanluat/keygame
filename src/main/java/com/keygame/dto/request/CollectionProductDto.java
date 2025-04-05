package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CollectionProductDto {
    @NotNull
    private Long collectionId;
    @NotEmpty
    private List<Long> productIds;
}
