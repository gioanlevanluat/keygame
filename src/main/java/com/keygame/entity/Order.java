package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userEmail;
    private String sendEmail;
    @Column(name = "sub_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal subPrice;
    @Column(name = "payment_fee", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal paymentFee;
    @Column(name = "total_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal totalPrice;
    private String orderStatus;
    private String paymentId;
    private String payerId;
    private String paymentMethod;
    private Date paymentDate;
    private Boolean isSendEmail;
}
