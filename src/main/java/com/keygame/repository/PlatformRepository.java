package com.keygame.repository;

import com.keygame.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    @Modifying
    @Query(value = "update Platform p set p.isDeleted = true where p.id = :id")
    int softDelete(Long id);
}
