package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "key")
@Getter
@Setter
@NoArgsConstructor
public class Key extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique=true)
    private String code;
    @NotNull
    private Long productId;
    private Long orderDetailId;
    private Long orderId;
    @NotNull
    private Boolean isOrdered = false;
    private String supplier;
    private Date orderDate;
    @Column(name = "base_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal basePrice;
    private Long inventoryId;
    private String codeType;
}
