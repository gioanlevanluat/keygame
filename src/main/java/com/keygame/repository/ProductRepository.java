package com.keygame.repository;

import com.keygame.dto.request.ProductBulkEditDto;
import com.keygame.dto.request.QuickSearchProductDto;
import com.keygame.dto.request.SearchProductDto;
import com.keygame.dto.response.ProductSimpleDto;
import com.keygame.dto.response.ProductSimpleInterfaceDto;
import com.keygame.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {

    @Query(value = "select new com.keygame.dto.response.ProductSimpleDto(p.id, p.name, p.handle, p.image, p.price, p.comparePrice, p.platform) " +
            " from Product p where p.isDeleted = false" +
            " and (:#{#dto.name} is null or upper(p.name) like %:#{#dto.name}%) " +
            " and (:#{#dto.platforms} is null or p.platform in :#{#dto.platforms}) " +
            " and (:#{#dto.regions} is null or p.regionId in :#{#dto.regions}) ")
    Page<ProductSimpleDto> quickSearch(QuickSearchProductDto dto, Pageable pageable);

    @Query(value = "select p.id as id, p.name as name, p.handle as handle, p.image as image, p.price as price, p.compare_price as comparePrice, p.platform as platform" +
            " from product p join collection_product cp on p.id = cp.product_id " +
            " join collection c on c.id = cp.collection_id " +
            " where c.code = :collectionCode and p.is_deleted = false and c.is_deleted = false " +
            " order by p.id desc", nativeQuery = true)
    Page<ProductSimpleInterfaceDto> findByCollectionCode(String collectionCode, Pageable pageable);

    @Query(value = "select p.id as id, p.name as name, p.handle as handle, p.image as image, p.price as price, p.compare_price as comparePrice, p.platform as platform" +
            " from product p join collection_product cp on p.id = cp.product_id " +
            " join collection c on c.id = cp.collection_id " +
            " where c.code = :collectionCode and p.is_deleted = false and c.is_deleted = false " +
            " limit :size ", nativeQuery = true)
    List<ProductSimpleInterfaceDto> getProductInMainPageCollection(String collectionCode, Integer size);

    @Query(value = "select new com.keygame.dto.response.ProductSimpleDto(p.id, p.name, p.handle, p.image, p.price, p.comparePrice, p.platform) " +
            " from Product p " +
            " where p.isDeleted = false " +
            " order by p.id desc")
    Page<ProductSimpleDto> findAllProjection(Pageable pageable);

    @Modifying
    @Query(value = "update Product p set p.isDeleted = true where p.id = :id")
    int softDelete(Long id);

    @Modifying
    @Query(value = "update Product p set p.price = :#{#bulkEditDto.price} where p.id in (:#{#bulkEditDto.productIds})")
    void bulkEditDto(ProductBulkEditDto bulkEditDto);

    @Query(value = "select p from Product p where p.isDeleted = false and p.handle = :handle")
    Product findByHandle(String handle);

    @Query(value = "select new com.keygame.dto.response.ProductSimpleDto(p.id, p.name, p.handle, p.image, p.price, p.comparePrice, p.platform) " +
            " from Product p " +
            " where p.isDeleted = false")
    List<ProductSimpleDto> findAllProduct();

}
