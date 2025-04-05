package com.keygame.repository;

import com.keygame.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query(value = "select c from Collection c where c.isDeleted = false")
    List<Collection> findAll();

    @Query(value = "select c from Collection c where c.isDeleted = false and c.code = :code")
    Collection findByCollectionCode(String code);

    @Modifying
    @Query(value = "update Collection c set c.isDeleted = true where c.id = :id")
    int softDelete(Long id);
}
