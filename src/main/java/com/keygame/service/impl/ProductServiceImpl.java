package com.keygame.service.impl;

import com.keygame.common.SortEnum;
import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.request.*;
import com.keygame.dto.response.FileStoreDto;
import com.keygame.dto.response.ProductInfoDto;
import com.keygame.dto.response.ProductSimpleDto;
import com.keygame.dto.response.ProductSimpleInterfaceDto;
import com.keygame.entity.Platform;
import com.keygame.entity.Product;
import com.keygame.repository.CollectionProductRepository;
import com.keygame.repository.FileStoreRepository;
import com.keygame.repository.PlatformRepository;
import com.keygame.repository.ProductRepository;
import com.keygame.service.ProductService;
import com.keygame.ultils.ProductUtils;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String ALL_PRODUCT = "all";
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FileStoreRepository fileStoreRepository;
    @Autowired
    PlatformRepository platformRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CollectionProductRepository collectionProductRepository;

    @Override
    public Page<ProductSimpleDto> findByCollectionCode(SearchProductInCollectionDto searchProductInCollectionDto) {
        if (StringUtils.isEmpty(searchProductInCollectionDto.getCollection()))
            return Page.empty();
        if (searchProductInCollectionDto.getPageNumber() < 1) {
            searchProductInCollectionDto.setPageNumber(1);
        }
        if (searchProductInCollectionDto.getPageSize() < 1 || searchProductInCollectionDto.getPageSize() > 100) {
            searchProductInCollectionDto.setPageSize(10);
        }
        Pageable pageable = PageRequest.of(searchProductInCollectionDto.getPageNumber() - 1, searchProductInCollectionDto.getPageSize());
        Page<ProductSimpleDto> result;
        if (ALL_PRODUCT.equalsIgnoreCase(searchProductInCollectionDto.getCollection())) {
            result = productRepository.findAllProjection(pageable);
            return new PageImpl<>(result.getContent(),
                    pageable, result.getTotalElements());
        } else {
            Page<ProductSimpleInterfaceDto> productSimpleInterfaceDtos = productRepository.findByCollectionCode(searchProductInCollectionDto.getCollection(), pageable);
            return new PageImpl<>(productSimpleInterfaceDtos.getContent().stream().map(entity -> modelMapper.map(entity, ProductSimpleDto.class)).collect(Collectors.toList()),
                    pageable, productSimpleInterfaceDtos.getTotalElements());
        }
    }

    @Override
    public List<ProductSimpleDto> getProductInMainPageCollection(String collectionCode, Integer size) {
        if (StringUtils.isEmpty(collectionCode))
            return new ArrayList<>();
        return productRepository.getProductInMainPageCollection(collectionCode, size)
                .stream().map(entity -> modelMapper.map(entity, ProductSimpleDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public ProductInfoDto findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty() || optionalProduct.get().isDeleted()) {
            throw new BusinessException(PRODUCT_NOT_FOUND);
        }
        ProductInfoDto productInfoDto = modelMapper.map(optionalProduct.get(), ProductInfoDto.class);

        List<FileStoreDto> fileStoreDtos = fileStoreRepository.findByProductId(productInfoDto.getId()).stream()
                .map(entity -> modelMapper.map(entity, FileStoreDto.class)).sorted(Comparator.comparing(FileStoreDto::getPosition)).toList();
        productInfoDto.setFiles(fileStoreDtos);

        productInfoDto.setCollectionIds(collectionProductRepository.findByProductId(id));
        return productInfoDto;
    }

    @Override
    public ProductInfoDto findByHandle(String handle) {
        Product product = productRepository.findByHandle(handle);
        if (product == null) {
            throw new BusinessException(PRODUCT_NOT_FOUND);
        }
        ProductInfoDto productInfoDto = modelMapper.map(product, ProductInfoDto.class);
        Optional<Platform> platform = platformRepository.findById(productInfoDto.getPlatform());
        platform.ifPresent(value -> productInfoDto.setPlatFormName(value.getName()));

        return productInfoDto;
    }

    @Override
    public Page<ProductSimpleDto> quickSearch(QuickSearchProductDto searchProductDto) {
        if (searchProductDto.getPageNumber() < 1) {
            searchProductDto.setPageNumber(1);
        }
        if (searchProductDto.getPageSize() < 1 || searchProductDto.getPageSize() > 100) {
            searchProductDto.setPageSize(10);
        }

        Sort sort = buildSort(searchProductDto.getSortProductEnum());

        if (!StringUtils.isEmpty(searchProductDto.getName())) {
            searchProductDto.setName(searchProductDto.getName().toUpperCase());
        }

        Pageable pageable = PageRequest.of(searchProductDto.getPageNumber() - 1, searchProductDto.getPageSize(), sort);
        return productRepository.quickSearch(searchProductDto, pageable);
    }

    private Sort buildSort(SortEnum.Product sortEnum) {
        if (sortEnum == null) {
            return null;
        }
        Sort sort = Sort.by("id").descending();
        if (sortEnum.equals(SortEnum.Product.NAME_ASC)) {
            sort.and(Sort.by("name").ascending());
        } else if (sortEnum.equals(SortEnum.Product.NAME_DESC)) {
            sort.and(Sort.by("name").descending());
        } else if (sortEnum.equals(SortEnum.Product.PRICE_ASC)) {
            sort.and(Sort.by("price").ascending());
        } else if (sortEnum.equals(SortEnum.Product.PRICE_DESC)) {
            sort.and(Sort.by("price").descending());
        }

        return sort;
    }

    @Override
    public Page<ProductSimpleDto> search(SearchProductDto searchProductDto) {
        if (searchProductDto.getPageNumber() < 1) {
            searchProductDto.setPageNumber(1);
        }
        if (searchProductDto.getPageSize() < 1 || searchProductDto.getPageSize() > 100) {
            searchProductDto.setPageSize(10);
        }
        Sort sort = buildSort(searchProductDto.getSortEnum());
        if (!StringUtils.isEmpty(searchProductDto.getName())) {
            searchProductDto.setName(searchProductDto.getName().toUpperCase());
        }

        Pageable pageable;
        if (sort == null) {
            pageable = PageRequest.of(searchProductDto.getPageNumber() - 1, searchProductDto.getPageSize());
        } else {
            pageable = PageRequest.of(searchProductDto.getPageNumber() - 1, searchProductDto.getPageSize(), sort);
        }
        return productRepository.search(searchProductDto, pageable);
    }

    @Override
    public Long store(ProductDto productDto) {
        Product product = productRepository.save(modelMapper.map(productDto, Product.class));
        product.setDeleted(false);
        product.setCreatedAt(new Date());
        product.setHandle(ProductUtils.convertToURLFormat(productDto.getName()));
        return product.getId();
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return productRepository.softDelete(id) > 0;
    }

    @Transactional
    @Override
    public boolean bulkEdit(ProductBulkEditDto bulkEditDto) {
        productRepository.bulkEditDto(bulkEditDto);
        return true;
    }

    @Override
    public List<ProductSimpleDto> findAll() {
        return productRepository.findAllProduct();
    }
}

