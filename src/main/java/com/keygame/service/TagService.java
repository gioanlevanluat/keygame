package com.keygame.service;

import com.keygame.dto.TagDto;

import java.util.List;

public interface TagService {

    boolean save(TagDto tagDto);

    boolean delete(Long id);

    List<TagDto> findAll();
}
