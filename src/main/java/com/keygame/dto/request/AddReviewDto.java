package com.keygame.dto.request;

import com.keygame.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddReviewDto extends BaseEntity {
    @NotNull
    @Size(min = 1, max = 5)
    private Integer star;
    private String comment;
    @NotNull
    private Long productId;
}
