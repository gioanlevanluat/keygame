package com.keygame.service.impl;

import com.keygame.dto.TagDto;
import com.keygame.entity.Tag;
import com.keygame.repository.TagRepository;
import com.keygame.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(TagDto tagDto) {
        tagRepository.save(modelMapper.map(tagDto, Tag.class));
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return tagRepository.softDelete(id) > 0;
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream().map(entity -> modelMapper.map(entity, TagDto.class)).collect(Collectors.toList());
    }
}
