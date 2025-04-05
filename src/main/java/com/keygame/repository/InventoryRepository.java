package com.keygame.repository;

import com.keygame.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "select inv from Inventory inv where inv.isDeleted = false and (:productId is null or inv.id = :productId) and (:isOutOfStock is null or inv.inStockQuantity < 10)")
    List<Inventory> getInventoryInfo(Long productId, Boolean isOutOfStock);

    @Query(value = "select inv from Inventory inv where inv.id = :id and inv.isDeleted = false")
    Optional<Inventory> findById(Long id);
}
