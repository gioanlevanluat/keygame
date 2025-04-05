package com.keygame.repository;

import com.keygame.dto.request.SearchProductDto;
import com.keygame.dto.response.ProductSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {
    Page<ProductSimpleDto> search(SearchProductDto dto, Pageable pageable);
}
