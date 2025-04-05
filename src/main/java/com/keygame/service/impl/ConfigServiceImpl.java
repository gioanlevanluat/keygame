package com.keygame.service.impl;

import com.keygame.dto.ConfigDto;
import com.keygame.dto.TagDto;
import com.keygame.entity.Config;
import com.keygame.entity.Tag;
import com.keygame.repository.ConfigRepository;
import com.keygame.repository.TagRepository;
import com.keygame.service.ConfigService;
import com.keygame.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(ConfigDto configDto) {
        configRepository.save(modelMapper.map(configDto, Config.class));
        return true;
    }

    @Override
    public List<ConfigDto> findAll() {
        return configRepository.findAll().stream().map(entity -> modelMapper.map(entity, ConfigDto.class)).collect(Collectors.toList());
    }

    @Override
    public ConfigDto findByCode(String code) {
        return modelMapper.map(configRepository.findByCode(code), ConfigDto.class);
    }
}
