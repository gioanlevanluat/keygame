package com.keygame.repository;

import com.keygame.entity.CollectionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionProductRepository extends JpaRepository<CollectionProduct, Long> {

    @Query(value = "select cp.collectionId from CollectionProduct cp where cp.productId = :productId")
    List<Long> findByProductId(Long productId);

}
