package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private Long userId;
    @Column(name = "price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal price;
    @Column(name = "compare_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal basePrice;
    private Long keyId;
    private String key;
    private String urlKeyImage;
    private String sku;
    private String codeType;
    private Boolean outOfKeys;
    private Boolean isMyStock;
    private String prepaidForgeOrderReference;
    private String prepaidForgeCustomOrderReference;
}
