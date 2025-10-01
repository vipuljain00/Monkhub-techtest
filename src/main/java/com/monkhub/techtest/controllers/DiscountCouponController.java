package com.monkhub.techtest.controllers;

import com.monkhub.techtest.entities.DiscountCoupon;
import com.monkhub.techtest.enums.DiscountType;
import com.monkhub.techtest.enums.UsageLimit;
import com.monkhub.techtest.models.DiscountCouponDTO;
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
    public ResponseEntity<DiscountCouponDTO> createCoupon(@RequestBody @Valid DiscountCouponDTO couponDto) {
        DiscountCouponDTO createdCoupon = discountCouponService.createCoupon(couponDto);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DiscountCouponDTO>> getAllCoupons() {
        List<DiscountCouponDTO> coupons = discountCouponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCouponDTO> getCouponById(@PathVariable Long id) {
        DiscountCouponDTO coupon = discountCouponService.getCouponById(id);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCouponDTO> updateCoupon(@PathVariable Long id, @RequestBody @Valid DiscountCouponDTO couponDto) {
        DiscountCouponDTO updatedCoupon = discountCouponService.updateCoupon(id, couponDto);
        return new ResponseEntity<>(updatedCoupon, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long id) {
        discountCouponService.deleteCoupon(id);
        return new ResponseEntity<>("Discount coupon deleted successfully", HttpStatus.OK);
    }
}

