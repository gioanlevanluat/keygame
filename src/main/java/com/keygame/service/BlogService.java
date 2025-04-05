package com.keygame.service;

import com.keygame.dto.BlogDto;

import java.util.List;

public interface BlogService {

    boolean save(BlogDto blogDto);

    boolean delete(Long id);

    List<BlogDto> findAll(String collection);

    BlogDto findById(Long id);
}
