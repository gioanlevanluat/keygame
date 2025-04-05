package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory extends BaseEntity {
    @Id
    private Long id;
    private Integer inStockQuantity;
    private Integer inputQuantity;
    private Integer saleQuantity;
}
