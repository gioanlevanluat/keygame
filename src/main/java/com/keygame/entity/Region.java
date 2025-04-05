package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "region")
@Getter
@Setter
@NoArgsConstructor
public class Region extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String image;
}
