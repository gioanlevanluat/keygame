package com.keygame.service;

import com.keygame.dto.PlatformDto;
import com.keygame.dto.TagDto;

import java.util.List;

public interface PlatformService {

    List<PlatformDto> findAll();
    Boolean save(PlatformDto platformDto);

    Boolean delete(Long id);
}
