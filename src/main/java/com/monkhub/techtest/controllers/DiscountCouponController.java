package com.monkhub.techtest.controllers;

import com.monkhub.techtest.models.DiscountCouponDTO;
import com.monkhub.techtest.models.DiscountCouponResponseDTO;
import com.monkhub.techtest.service.DiscountCouponService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount-coupons")
@AllArgsConstructor
public class DiscountCouponController {

    @Autowired
    private final DiscountCouponService discountCouponService;

    @PostMapping
    public ResponseEntity<DiscountCouponResponseDTO> createCoupon(@RequestBody @Valid DiscountCouponDTO couponDto) {
        DiscountCouponResponseDTO createdCoupon = discountCouponService.createCoupon(couponDto);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DiscountCouponResponseDTO>> getAllCoupons() {
        List<DiscountCouponResponseDTO> coupons = discountCouponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCouponResponseDTO> getCouponById(@PathVariable Long id) {
        DiscountCouponResponseDTO coupon = discountCouponService.getCouponById(id);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCouponResponseDTO> updateCoupon(@PathVariable Long id, @RequestBody @Valid DiscountCouponDTO couponDto) {
        DiscountCouponResponseDTO updatedCoupon = discountCouponService.updateCoupon(id, couponDto);
        return new ResponseEntity<>(updatedCoupon, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long id) {
        discountCouponService.deleteCoupon(id);
        return new ResponseEntity<>("Discount coupon deleted successfully", HttpStatus.OK);
    }
}

