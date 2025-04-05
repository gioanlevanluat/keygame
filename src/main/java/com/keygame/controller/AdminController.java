package com.keygame.controller;

import com.keygame.common.OrderStatusEnum;
import com.keygame.config.ResponseConfig;
import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.BlogDto;
import com.keygame.dto.PlatformDto;
import com.keygame.dto.request.*;
import com.keygame.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    FileStoreService fileStoreService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;
    @Autowired
    PlatformService platformService;
    @Autowired
    BlogService blogService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    RegionService regionService;
    @Autowired
    KeyService keyService;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity getAllUser() {
        return ResponseConfig.success(userService.getListUser());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return ResponseConfig.success(userService.delete(id));
    }

    @PostMapping("/file/upload")
    public ResponseEntity uploadFile(@RequestParam MultipartFile file, @RequestParam Integer position) {
        return ResponseConfig.success(fileStoreService.uploadFile(file, position));
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity deleteFile(@PathVariable Long id) {
        return ResponseConfig.success(fileStoreService.deleteFile(id));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity loadFile(@PathVariable Long id) {
        Resource resource = fileStoreService.loadResourceFile(id);
        if (resource == null) {
            throw new BusinessException("File not found.");
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
    }

    @PostMapping("/file/attach-product")
    @Operation(summary = "Map file vào product")
    public ResponseEntity attachFileInProduct(@RequestBody @Valid AttachAllFileDto attachAllFileDto) {
        return ResponseConfig.success(fileStoreService.attachProduct(attachAllFileDto));
    }

    @PostMapping({"/file/move"})
    @Operation(summary = "Thay đổi vị trí 2 image")
    public ResponseEntity movePosition(@RequestBody @Valid MovePositionFileDto movePositionFileDto) {
        return ResponseConfig.success(fileStoreService.movePosition(movePositionFileDto));
    }

    @PostMapping({"/file/update-position"})
    @Operation(summary = "Thay đổi vị trí image")
    public ResponseEntity updatePosition(@RequestBody @Valid UpdatePositionFileDto updatePositionFileDto) {
        return ResponseConfig.success(fileStoreService.updatePosition(updatePositionFileDto));
    }

    @PostMapping("/collection")
    @Operation(summary = "Save update collection")
    public ResponseEntity saveCollection(@RequestBody @Valid CollectionDto collectionDto) {
        return ResponseConfig.success(collectionService.save(collectionDto));
    }

    @GetMapping("/collections")
    @Operation(summary = "Get collection")
    public ResponseEntity getAllCollection() {
        return ResponseConfig.success(collectionService.findAll());
    }

    @DeleteMapping("collection/{id}")
    @Operation(summary = "Delete collection")
    public ResponseEntity deleteCollection(@PathVariable Long id) {
        return ResponseConfig.success(collectionService.delete(id));
    }

    @PostMapping("collection/add-product")
    @Operation(summary = "Thêm product vào collection")
    public ResponseEntity addProduct(@RequestBody @Valid CollectionProductDto collectionProductDto) {
        return ResponseConfig.success(collectionService.addProduct(collectionProductDto));
    }

    @PostMapping("product/add-collection")
    @Operation(summary = "Thêm collection vào product")
    public ResponseEntity addCollection(@RequestBody @Valid AddCollectionInProductDto addCollectionInProductDto) {
        return ResponseConfig.success(collectionService.addCollectionInProduct(addCollectionInProductDto));
    }

    @PostMapping("/search/product")
    @Operation(summary = "Search product")
    public ResponseEntity searchProduct(@RequestBody @Valid SearchProductDto searchProductDto) {
        return ResponseConfig.success(productService.search(searchProductDto));
    }

    @PostMapping("/product/find-all")
    @Operation(summary = "Find All product")
    public ResponseEntity findAllProduct() {
        return ResponseConfig.success(productService.findAll());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseConfig.success(productService.findById(id));
    }

    @PostMapping("/product")
    public ResponseEntity store(@RequestBody @Valid ProductDto productDto) {
        return ResponseConfig.success(productService.store(productDto));
    }

    @PostMapping("/product/bulk-edit")
    @Operation(summary = "Sửa cùng lúc nhiều product")
    public ResponseEntity bulkEdit(@RequestBody ProductBulkEditDto bulkEditDto) {
        return ResponseConfig.success(productService.bulkEdit(bulkEditDto));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        return ResponseConfig.success(productService.delete(id));
    }

    @GetMapping("/platforms")
    public ResponseEntity getAllPlatform() {
        return ResponseConfig.success(platformService.findAll());
    }

    @PostMapping("/platform")
    public ResponseEntity savePlatform(@RequestBody PlatformDto platformDto) {
        return ResponseConfig.success(platformService.save(platformDto));
    }

    @DeleteMapping("/platform/{id}")
    public ResponseEntity deletePlatform(@PathVariable Long id) {
        return ResponseConfig.success(platformService.delete(id));
    }

    @GetMapping("/collection/blog")
    public ResponseEntity getAll(@RequestParam String collection) {
        return ResponseConfig.success(blogService.findAll(collection));
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity getBlogById(@PathVariable Long id) {
        return ResponseConfig.success(blogService.findById(id));
    }

    @PostMapping("/blog")
    public ResponseEntity save(@RequestBody @Valid BlogDto blogDto) {
        return ResponseConfig.success(blogService.save(blogDto));
    }

    @GetMapping("/order/status")
    public ResponseEntity getAllOrderStatus() {
        return ResponseConfig.success(OrderStatusEnum.values());
    }

    @PostMapping("/order/search-order")
    public ResponseEntity searchOrder(@RequestBody @Valid ReportOrderDto reportOrderDto) {
        return ResponseConfig.success(orderService.searchOrder(reportOrderDto));
    }

    @PostMapping("/order/search-order-detail")
    public ResponseEntity searchOrder(@RequestBody @Valid ReportOrderDetailDto requestReportOrderDto) {
        return ResponseConfig.success(orderDetailService.searchOrderDetail(requestReportOrderDto));
    }

    @GetMapping("/regions")
    public ResponseEntity getAlRegion() {
        return ResponseConfig.success(regionService.findAll());
    }

    @PostMapping("/key/add-key")
    public ResponseEntity addKey(@RequestBody @Valid AddKeyDto addKeyDto) {
        return ResponseConfig.success(keyService.save(addKeyDto));
    }

    @DeleteMapping("/key/{id}")
    public ResponseEntity deleteKey(@PathVariable Long keyId) {
        return ResponseConfig.success(keyService.delete(keyId));
    }

    @PostMapping("/search-keys")
    public ResponseEntity searchKey(@RequestBody @Valid SearchKeyDto searchKeyDto) {
        return ResponseConfig.success(keyService.findAll(searchKeyDto));
    }
}