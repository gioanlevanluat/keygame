package com.keygame.controller;

import com.keygame.config.ResponseConfig;
import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.request.GetReviewDto;
import com.keygame.dto.request.QuickSearchProductDto;
import com.keygame.dto.request.SearchProductDto;
import com.keygame.dto.request.SearchProductInCollectionDto;
import com.keygame.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PageController {

    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    CollectionService collectionService;

    @Autowired
    ProductService productService;
    @Autowired
    PlatformService platformService;
    @Autowired
    MenuService menuService;
    @Autowired
    BlogService blogService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PaymentMethodService paymentMethodService;

    @Autowired
    ConfigService configService;

    @Autowired
    RegionService regionService;

    @Value("${main.page.collection}")
    private String mainPageCollectionConfig;

//    @GetMapping("/file/{id}")
//    @Operation(summary = "Lấy data file")
//    public ResponseEntity<?> loadResourceFile(@PathVariable Long id) {
//        Resource resource = fileStoreService.loadResourceFile(id);
//        if (resource == null) {
//            throw new BusinessException("File not found.");
//        }
//        String contentType = "application/octet-stream";
//        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
//
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
//    }

    @GetMapping("/image/{type}/{name}")
    @Operation(summary = "Lấy hình ảnh")
    public ResponseEntity<?> loadProductImage(@PathVariable String type, @PathVariable String name) {
        Resource resource = fileStoreService.loadResourceFileByName(type, name);
        if (resource == null) {
            throw new BusinessException("File not found.");
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
    }

    @GetMapping("/page/collection-list")
    public ResponseEntity getCollectionList() {
        return ResponseConfig.success(configService.findByCode(mainPageCollectionConfig).getValue());
    }

    @GetMapping("/page/collection-info")
    public ResponseEntity getCollectionInfoMainPage(@RequestParam String code, @RequestParam Integer size) {
        log.error("code: " + code);
        return ResponseConfig.success(collectionService.getCollectionInMainPage(code, size));
    }

    @GetMapping(value = "/product/{handel}")
    @Operation(summary = "Lấy thông tin chi tiết product theo handle")
    public ResponseEntity findByHandle(@PathVariable String handle) {
        return ResponseConfig.success(productService.findByHandle(handle));
    }

    @PostMapping("/search/product")
    @Operation(summary = "Search product")
    public ResponseEntity searchProduct(@RequestBody @Valid SearchProductDto searchProductDto) {
        return ResponseConfig.success(productService.search(searchProductDto));
    }

    @GetMapping("/collection/product")
    @Operation(summary = "Danh sách product theo collection")
    public ResponseEntity findByCollectionCode(@Valid SearchProductInCollectionDto searchProductInCollectionDto) {
        return ResponseConfig.success(productService.findByCollectionCode(searchProductInCollectionDto));
    }

    @GetMapping("/platform")
    public ResponseEntity getAllPlatform() {
        return ResponseConfig.success(platformService.findAll());
    }

    @GetMapping("/menus")
    public ResponseEntity getMenus() {
        return ResponseConfig.success(menuService.findAll());
    }

    @GetMapping("/blog/{id}")
    @Operation(summary = "Get chi tiết blog")
    public ResponseEntity getBlog(@PathVariable Long id) {
        return ResponseConfig.success(blogService.findById(id));
    }

    @GetMapping("/collection/blog")
    @Operation(summary = "Get danh sách blog theo trang")
    public ResponseEntity getAll(@RequestParam String collection) {
        return ResponseConfig.success(blogService.findAll(collection));
    }

    @PostMapping("/reviews")
    public ResponseEntity getReview(@RequestBody @Valid GetReviewDto getReviewDto) {
        return ResponseConfig.success(reviewService.getReviewOfProduct(getReviewDto));
    }

    @GetMapping("/payment-methods")
    @Operation(summary = "Get danh sách phương thức thanh toán")
    public ResponseEntity getAllMethod() {
        return ResponseConfig.success(paymentMethodService.getAll());
    }

    @GetMapping("/regions")
    public ResponseEntity getAlRegion() {
        return ResponseConfig.success(regionService.findAll());
    }
}
