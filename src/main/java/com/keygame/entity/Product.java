package com.keygame.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product", indexes = {@Index(name = "idx_handle", columnList="handle")})
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String handle;
    private String image;
    private Long parentId;
    private String description;
    private String requirement;
    @NotNull
    @Column(name = "base_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal basePrice;
    @NotNull
    @Column(name = "price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal price;
    @Column(name = "compare_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal comparePrice;
    private Integer platform;
    private Float discount;
    private String regionId;
    private String regionName;
    private String regionImage;
    private Integer brandId;
    private String brandName;
    private String brandImage;
    private Date releaseDate;
    private String developer;
    private String publisher;
    private String supportedLanguages;
    private Long dealEnd;
    private String note;
    private Boolean autoSendKey = false;
    private String sku;
    private String supplier;
    @Column(name = "supplier_price", precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
    private BigDecimal supplierPrice;
    private String codeType;
}
