package com.keygame.repository;

import com.keygame.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Modifying
    @Query(value = "update Blog t set t.isDeleted = true where t.id = :id")
    int softDelete(Long id);

    @Query(value = "select t from Blog t where t.isDeleted = false")
    List<Blog> findAll();

    @Query(value = "select t from Blog t where t.isDeleted = false and t.collection = :collection")
    List<Blog> findAll(String collection);

}
