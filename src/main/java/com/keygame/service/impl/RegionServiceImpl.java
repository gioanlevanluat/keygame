package com.keygame.service.impl;

import com.keygame.dto.response.RegionDto;
import com.keygame.repository.RegionRepository;
import com.keygame.service.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RegionDto> findAll() {
        return regionRepository.findAll().stream().map(entity -> modelMapper.map(entity, RegionDto.class)).toList();
    }
}
