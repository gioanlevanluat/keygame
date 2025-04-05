package com.keygame.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReportOrderDto {
    private Boolean isSendEmail;
    private List<String> orderStatus;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date beginCreateAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endCreatedAt;
    private Integer pageSize;
    private Integer pageNumber;
    private String sort;
}