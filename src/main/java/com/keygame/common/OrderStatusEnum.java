package com.keygame.common;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    SUBMITTED, PAYMENT_PROCESS, CANCEL_ORDER, PAYMENT_FAIL, PAYMENT_SUCCESS, GET_KEY_SUCCESS, GET_KEY_FAIL, SEND_MAIL_FAIL, SEND_MAIL_SUCCESS;
}
