package com.keygame.service;

import com.keygame.dto.ConfigDto;
import com.keygame.dto.TagDto;

import java.util.List;

public interface ConfigService {

    boolean save(ConfigDto configDto);

    List<ConfigDto> findAll();

    ConfigDto findByCode(String code);
}
