package com.keygame.service;

import com.keygame.dto.request.AddKeyDto;
import com.keygame.dto.request.SearchKeyDto;
import com.keygame.dto.response.KeyInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KeyService {

    boolean save(AddKeyDto addKeyDto);

    boolean delete(Long id);

    Page<KeyInfoDto> findAll(SearchKeyDto searchKeyDto);
}
