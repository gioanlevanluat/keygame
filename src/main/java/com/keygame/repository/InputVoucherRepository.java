package com.keygame.repository;

import com.keygame.entity.Blog;
import com.keygame.entity.InputVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputVoucherRepository extends JpaRepository<InputVoucher, Long> {

    @Modifying
    @Query(value = "update InputVoucher t set t.isDeleted = true where t.id = :id")
    int softDelete(Long id);

    @Query(value = "select t from InputVoucher t where t.isDeleted = false")
    List<InputVoucher> findAll();

}
