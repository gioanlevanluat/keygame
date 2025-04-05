package com.keygame.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "file_store")
@Getter
@Setter
@NoArgsConstructor
public class FileStore extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String path;
    @NotNull
    private String name;
    private Integer type;
    private Long productId;
    private Long ticketId;
    private Integer position;
    private Boolean isPublic = false;
}
