package com.keygame.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Integer star;
    private String title;
    private String comment;
    private Long productId;
    private Long userId;
    private String username;
    private Date createdAt;
}
