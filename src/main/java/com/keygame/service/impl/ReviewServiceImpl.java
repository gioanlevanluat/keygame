package com.keygame.service.impl;

import com.keygame.dto.request.AddReviewDto;
import com.keygame.dto.request.GetReviewDto;
import com.keygame.dto.response.ReviewDto;
import com.keygame.entity.Review;
import com.keygame.repository.ReviewRepository;
import com.keygame.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(AddReviewDto addReviewDto, HttpServletRequest httpServletRequest) {
        reviewRepository.save(modelMapper.map(addReviewDto, Review.class));
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return reviewRepository.softDelete(id) > 0;
    }

    @Override
    public Page<ReviewDto> getReviewOfProduct(GetReviewDto getReviewDto) {
        if (getReviewDto.getPageNumber() < 1) {
            getReviewDto.setPageNumber(1);
        }
        if (getReviewDto.getPageSize() < 1 || getReviewDto.getPageSize() > 100) {
            getReviewDto.setPageSize(1);
        }
        Pageable pageable = PageRequest.of(getReviewDto.getPageNumber() - 1, getReviewDto.getPageSize());
        return reviewRepository.getReviewOfProduct(getReviewDto.getProductId(), getReviewDto.getStar(), pageable);
    }
}
