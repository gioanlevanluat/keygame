package com.keygame.service;

import com.keygame.dto.request.AddCollectionInProductDto;
import com.keygame.dto.request.CollectionDto;
import com.keygame.dto.request.CollectionProductDto;
import com.keygame.dto.response.CollectionInfo;

import java.util.List;

public interface CollectionService {

    boolean save(CollectionDto collectionDto);

    boolean delete(Long id);

    List<CollectionDto> findAll();

    boolean addProduct(CollectionProductDto collectionProductDto);

    boolean addCollectionInProduct(AddCollectionInProductDto addCollectionInProductDto);

    CollectionInfo getCollectionInMainPage(String code, Integer size);
}
