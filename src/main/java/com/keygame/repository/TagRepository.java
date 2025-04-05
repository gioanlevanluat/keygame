package com.keygame.repository;

import com.keygame.entity.Collection;
import com.keygame.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Modifying
    @Query(value = "update Tag t set t.isDeleted = true where t.id = :id")
    int softDelete(Long id);

    @Query(value = "select t from Tag t where t.isDeleted = false")
    List<Tag> findAll();
}
