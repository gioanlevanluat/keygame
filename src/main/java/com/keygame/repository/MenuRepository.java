package com.keygame.repository;

import com.keygame.entity.Menu;
import com.keygame.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "select m from Menu m where m.isDeleted = false order by m.name asc")
    List<Menu> findAll();

    @Query(value = "select m.id from Menu m where m.isDeleted = false and m.code = :code")
    Long findByCode(String code);
}
