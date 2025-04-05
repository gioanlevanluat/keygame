package com.keygame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private String title;
    private String content;
    private List<String> files;
    private Long userId;
    private String userName;
    private String userEmail;
}
