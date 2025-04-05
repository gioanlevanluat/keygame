package com.keygame.service.impl;

import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.BlogDto;
import com.keygame.entity.Blog;
import com.keygame.repository.BlogRepository;
import com.keygame.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(BlogDto blogDto) {
        blogRepository.save(modelMapper.map(blogDto, Blog.class));
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return blogRepository.softDelete(id) > 0;
    }

    @Override
    public List<BlogDto> findAll(String collection) {
        if (StringUtils.isEmpty(collection))
            return blogRepository.findAll().stream().map(entity -> modelMapper.map(entity, BlogDto.class)).collect(Collectors.toList());
        else
            return blogRepository.findAll(collection).stream().map(entity -> modelMapper.map(entity, BlogDto.class)).collect(Collectors.toList());
    }

    public static final String BLOG_NOT_FOUND = "Blog not found.";
    @Override
    public BlogDto findById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (!blog.isPresent() || blog.get().isDeleted()) {
            throw new BusinessException(BLOG_NOT_FOUND);
        }
        return modelMapper.map(blog.get(), BlogDto.class);
    }
}
