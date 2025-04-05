package com.keygame.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "input_voucher")
@Getter
@Setter
@NoArgsConstructor
public class InputVoucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer inputQuantity;
    @Column(name = "base_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal basePrice;
}
