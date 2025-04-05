package com.keygame.service.impl;

import com.keygame.dto.PlatformDto;
import com.keygame.entity.Platform;
import com.keygame.repository.PlatformRepository;
import com.keygame.service.PlatformService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformServiceImpl implements PlatformService {
    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PlatformDto> findAll() {
        return platformRepository.findAll().stream()
                .filter(entity -> !entity.isDeleted())
                .map(entity -> modelMapper.map(entity, PlatformDto.class))
                .toList();
    }

    @Override
    public Boolean save(PlatformDto platformDto) {
        platformRepository.save(modelMapper.map(platformDto, Platform.class));
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        platformRepository.softDelete(id);
        return true;
    }
}
