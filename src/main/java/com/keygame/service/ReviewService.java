package com.keygame.service;

import com.keygame.dto.request.AddReviewDto;
import com.keygame.dto.request.GetReviewDto;
import com.keygame.dto.response.ReviewDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {

    boolean save(AddReviewDto addReviewDto, HttpServletRequest httpServletRequest);

    boolean delete(Long id);

    Page<ReviewDto> getReviewOfProduct(GetReviewDto getReviewDto);
}
