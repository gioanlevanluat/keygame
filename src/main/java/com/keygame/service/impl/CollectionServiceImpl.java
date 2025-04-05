package com.keygame.service.impl;

import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.request.AddCollectionInProductDto;
import com.keygame.dto.request.CollectionDto;
import com.keygame.dto.request.CollectionProductDto;
import com.keygame.dto.response.CollectionInfo;
import com.keygame.dto.response.ProductSimpleDto;
import com.keygame.entity.Collection;
import com.keygame.entity.CollectionProduct;
import com.keygame.repository.CollectionProductRepository;
import com.keygame.repository.CollectionRepository;
import com.keygame.repository.ProductRepository;
import com.keygame.service.CollectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionProductRepository collectionProductRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Override
    public boolean save(CollectionDto collectionDto) {
        collectionRepository.save(modelMapper.map(collectionDto, Collection.class));
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return collectionRepository.softDelete(id) > 0;
    }

    @Override
    public List<CollectionDto> findAll() {
        return collectionRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CollectionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addProduct(CollectionProductDto collectionProductDto) {
        if (collectionProductDto.getCollectionId() == null)
            return false;
        if (CollectionUtils.isEmpty(collectionProductDto.getProductIds()))
            return false;
        collectionProductDto.getProductIds().stream().distinct().filter(Objects::nonNull)
                .forEach(productId -> {
                    CollectionProduct cp = new CollectionProduct();
                    cp.setCollectionId(collectionProductDto.getCollectionId());
                    cp.setProductId(productId);
                    collectionProductRepository.save(cp);
                });
        return true;
    }

    @Override
    public boolean addCollectionInProduct(AddCollectionInProductDto addCollectionInProductDto) {
        if (addCollectionInProductDto.getProductId() == null)
            return false;
        if (CollectionUtils.isEmpty(addCollectionInProductDto.getCollectionIds()))
            return false;
        addCollectionInProductDto.getCollectionIds().stream().distinct().filter(Objects::nonNull)
                .forEach(collectionId -> {
                    CollectionProduct cp = new CollectionProduct();
                    cp.setProductId(addCollectionInProductDto.getProductId());
                    cp.setCollectionId(collectionId);
                    collectionProductRepository.save(cp);
                });
        return true;
    }

    @Override
    public CollectionInfo getCollectionInMainPage(String code, Integer size) {
        Collection collection = collectionRepository.findByCollectionCode(code);
        if (collection == null) throw new BusinessException("Collection not found!");
        CollectionInfo collectionInfo = modelMapper.map(collection, CollectionInfo.class);
        List<ProductSimpleDto> products = productRepository.getProductInMainPageCollection(code, size)
                .stream().map(entity -> modelMapper.map(entity, ProductSimpleDto.class))
                .toList();
        collectionInfo.setProducts(products);
        return collectionInfo;
    }
}
