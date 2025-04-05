package com.keygame.controller;

import com.keygame.config.ResponseConfig;
import com.keygame.dto.response.SimpleUserResponse;
import com.keygame.dto.request.AddReviewDto;
import com.keygame.dto.request.CreateOrderDto;
import com.keygame.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/user-profile")
    public SimpleUserResponse getUserProfile(HttpServletRequest req) {
        return userService.getProfile(req);
    }

    @PostMapping("/create-order")
    public ResponseEntity createOrder(@RequestBody @Valid CreateOrderDto createOrderDto, HttpServletRequest httpServletRequest) {
        return ResponseConfig.success(orderService.save(createOrderDto, httpServletRequest));
    }

    @GetMapping("/orders")
    public ResponseEntity getOrderInfo(HttpServletRequest request) {
        return ResponseConfig.success(orderService.findAll(request));
    }

    @PostMapping("/review")
    public ResponseEntity addReview(@RequestBody @Valid AddReviewDto addReviewDto, HttpServletRequest httpServletRequest) {
        return ResponseConfig.success(reviewService.save(addReviewDto, httpServletRequest));
    }
}
