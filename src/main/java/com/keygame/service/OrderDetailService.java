package com.keygame.service;

import com.keygame.dto.TagDto;
import com.keygame.dto.request.ReportOrderDetailDto;
import com.keygame.dto.response.ResponseReportOrderDetailDto;

import java.util.List;

public interface OrderDetailService {

    ResponseReportOrderDetailDto searchOrderDetail(ReportOrderDetailDto reportOrderDetailDto);
}
