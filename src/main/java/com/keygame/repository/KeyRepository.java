package com.keygame.repository;

import com.keygame.dto.request.SearchKeyDto;
import com.keygame.dto.response.KeyInfoDto;
import com.keygame.entity.Blog;
import com.keygame.entity.Key;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {

    @Query(value = "select k from Key k where k.isDeleted = false and k.isOrdered = false and k.productId = :productId")
    Key getKey(Long productId);

    @Modifying
    @Query(value = "update Key k set k.isDeleted = true where k.id = :id")
    int softDelete(Long id);

    @Query(value = "select k from Key k where 1 = 1 " +
            " and (:#{#searchKeyDto.productIds} is null or k.productId in :#{#searchKeyDto.productIds})" +
            " and (:#{#searchKeyDto.isOrdered} is null or k.isOrdered = :#{#searchKeyDto.isOrdered}) ")
    Page<Key> searchKey(SearchKeyDto searchKeyDto, Pageable pageable);
}