package com.keygame.repository;

import com.keygame.entity.Region;
import com.keygame.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = "select r from Region r where r.isDeleted = false")
    List<Region> findAll();
}
