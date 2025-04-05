package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReportOrderDetailDto {
    private Long productId;
    private String key;
    private Long orderId;
    private Boolean autoSendKey;
    private Boolean outOfKeys;
    @NotNull
    private Date beginCreateAt;
    @NotNull
    private Date endCreateAt;
}
