package com.keygame.repository;

import com.keygame.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStoreRepository extends JpaRepository<FileStore, Long> {

    @Query(value = "select f from FileStore f where f.isDeleted = false and f.productId = :productId")
    List<FileStore> findByProductId(Long productId);
}
