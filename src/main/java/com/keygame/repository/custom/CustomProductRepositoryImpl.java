package com.keygame.repository.custom;

import com.keygame.dto.request.SearchProductDto;
import com.keygame.dto.response.ProductSimpleDto;
import com.keygame.repository.CustomProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CustomProductRepositoryImpl implements CustomProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ProductSimpleDto> search(SearchProductDto dto, Pageable pageable) {
        StringBuilder selectBuilder = new StringBuilder("select new com.keygame.dto.response.ProductSimpleDto( " +
                " p.id, p.name, p.handle, p.image, p.price, p.comparePrice, p.platform" +
                ", p.brandId, p.brandName, p.brandImage" +
                ", p.regionId, p.regionName, p.regionImage, p.discount) ");
        StringBuilder countBuilder = new StringBuilder("select count(p.id) ");
        StringBuilder queryBuilder = new StringBuilder(" from Product p ");
        Map<String, Object> params = new HashMap<>();
        if (!CollectionUtils.isEmpty(dto.getCollectionIds())) {
            queryBuilder.append(" JOIN CollectionProduct cp ON cp.productId = p.id ");
        }

        queryBuilder.append(" where p.isDeleted = false ");
        if (StringUtils.hasLength(dto.getName())) {
            queryBuilder.append(" and upper(p.name) like concat(concat('%', :name), '%') ");
            params.put("name", dto.getName());
        }
        if (!CollectionUtils.isEmpty(dto.getPlatforms())) {
            queryBuilder.append(" and p.platform in :platforms ");
            params.put("platforms", dto.getPlatforms());
        }
        if (!CollectionUtils.isEmpty(dto.getRegionIds())) {
            queryBuilder.append(" and p.regionId in :regions ");
            params.put("regions", dto.getRegionIds());
        }
        if (!CollectionUtils.isEmpty(dto.getCollectionIds())) {
            queryBuilder.append(" and cp.collectionId in :collectionIds ");
            params.put("collectionIds", dto.getCollectionIds());
        }

        StringBuilder orderBuilder = new StringBuilder();
        if (dto.getSortEnum() != null) {
            orderBuilder.append(" ORDER BY p.").append(dto.getSortEnum().toString().toLowerCase().replace("_", " "));
        }

        TypedQuery<ProductSimpleDto> query = entityManager.createQuery(selectBuilder.append(queryBuilder).append(orderBuilder).toString(), ProductSimpleDto.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults((pageable.getPageNumber() + 1) * pageable.getPageSize());
        params.forEach(query::setParameter);

        TypedQuery<Long> countQuery = entityManager.createQuery(countBuilder.append(queryBuilder).toString(), Long.class);
        params.forEach(countQuery::setParameter);
        return new PageImpl<>(query.getResultList(),
                pageable, countQuery.getSingleResult());
    }
}
