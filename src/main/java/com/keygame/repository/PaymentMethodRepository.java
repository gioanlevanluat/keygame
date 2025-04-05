package com.keygame.repository;

import com.keygame.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query(value = "select p from PaymentMethod p where p.code = :code and p.isDeleted = false")
    PaymentMethod findByCode(String code);

    @Query(value = "select p from PaymentMethod p where p.isDeleted = false")
    List<PaymentMethod> getAll();
}
