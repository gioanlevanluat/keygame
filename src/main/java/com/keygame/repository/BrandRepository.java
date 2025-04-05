package com.keygame.repository;

import com.keygame.entity.Brand;
import com.keygame.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "select b.id from Brand b where b.isDeleted = false and b.name = :name")
    Long findByName(String name);
}
