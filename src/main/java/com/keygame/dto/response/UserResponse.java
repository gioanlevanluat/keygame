package com.keygame.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keygame.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private boolean isSubscriber;
    private Set<Role> roles = new HashSet<>();
    private boolean isActive;
    private boolean isDeleted;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;
}
