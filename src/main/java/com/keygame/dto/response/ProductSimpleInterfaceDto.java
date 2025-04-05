package com.keygame.dto.response;

import java.math.BigDecimal;

public interface ProductSimpleInterfaceDto {
     Long getId();
     String getName();
     String getHandle();
     String getImage();
     BigDecimal getPrice();
     BigDecimal getComparePrice();
     Integer getPlatform();
}
