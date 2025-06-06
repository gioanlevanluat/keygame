package com.keygame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GooglePojo {
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
}