package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_info")
@Getter
@Setter
@NoArgsConstructor
public class PaymentInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long customerId;
    private String paymentId;
    private String payerId;
    private String paymentMethod;
    private String paymentErrorName;
    private String paymentErrorMessage;
    private String orderStatus;
}
