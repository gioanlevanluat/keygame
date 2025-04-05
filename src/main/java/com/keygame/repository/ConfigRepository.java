package com.keygame.repository;

import com.keygame.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

    @Query("select c from Config c where c.code = :code and c.isDeleted = false")
    Config findByCode(String code);
}
