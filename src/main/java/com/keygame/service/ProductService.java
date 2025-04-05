package com.keygame.service;

import com.keygame.dto.request.*;
import com.keygame.dto.response.CartInfoDto;
import com.keygame.dto.response.ProductSimpleDto;
import com.keygame.dto.response.ProductInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<ProductSimpleDto> findByCollectionCode(SearchProductInCollectionDto searchProductInCollectionDto);
    List<ProductSimpleDto> getProductInMainPageCollection(String collectionCode, Integer size);
    ProductInfoDto findById (Long id);

    ProductInfoDto findByHandle (String handle);

    Page<ProductSimpleDto> quickSearch(QuickSearchProductDto quickSearchProductDto);

    Page<ProductSimpleDto> search(SearchProductDto searchProductDto);

    Long store(ProductDto productDto);

    boolean delete(Long id);

    boolean bulkEdit(ProductBulkEditDto bulkEditDto);

    List<ProductSimpleDto> findAll();
}
