package com.keygame.repository;

import com.keygame.dto.response.ReviewDto;
import com.keygame.entity.Review;
import com.keygame.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Query(value = "update Review r set r.isDeleted = true where r.id = :id")
    int softDelete(Long id);

    @Query(value = "select new com.keygame.dto.response.ReviewDto(r.id, r.star, r.title, r.comment, r.productId, r.userId, r.username, r.createdAt) " +
            " from Review r where r.isDeleted = false and r.productId = :productId and (:star is null or r.star = :star) order by r.star desc, r.createdAt desc")
    Page<ReviewDto> getReviewOfProduct(Long productId, Integer star, Pageable pageable);
}