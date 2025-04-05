package com.keygame.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

import static com.keygame.common.ConfigConstant.DD_MM_YYYY_HH24_MM_SS;

@Getter
@Setter
@NoArgsConstructor
public class OrderFullDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private String sendEmail;
    private BigDecimal subPrice;
    private BigDecimal paymentFee;
    private BigDecimal totalPrice;
    private String orderStatus;
    private String paymentId;
    private String payerId;
    private String paymentMethod;
    @JsonFormat(pattern = DD_MM_YYYY_HH24_MM_SS)
    private Date paymentDate;
    private Boolean isSendEmail;
    @JsonFormat(pattern = DD_MM_YYYY_HH24_MM_SS)
    private Date createdAt;
    private String createdBy;
    @JsonFormat(pattern = DD_MM_YYYY_HH24_MM_SS)
    private Date updatedAt;
    private String updatedBy;
    private boolean isDeleted;
}
