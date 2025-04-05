package com.keygame.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddCollectionInProductDto {
    @NotEmpty
    private List<Long> collectionIds;

    @NotNull
    private Long productId;
}
