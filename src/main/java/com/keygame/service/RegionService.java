package com.keygame.service;

import com.keygame.dto.response.RegionDto;

import java.util.List;

public interface RegionService {

    List<RegionDto> findAll();
}
