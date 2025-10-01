package com.monkhub.techtest.service;

import com.monkhub.techtest.entities.DiscountCoupon;
import com.monkhub.techtest.entities.Customer;
import com.monkhub.techtest.models.DiscountCouponDTO;
import com.monkhub.techtest.models.DiscountCouponResponseDTO;
import com.monkhub.techtest.repository.DiscountCouponRepository;
import com.monkhub.techtest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiscountCouponService {

    @Autowired
    private final DiscountCouponRepository discountCouponRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    public DiscountCouponResponseDTO createCoupon(DiscountCouponDTO couponDTO) {
        DiscountCoupon coupon = mapToEntity(couponDTO);
        DiscountCoupon savedCoupon = discountCouponRepository.save(coupon);
        return mapToDTO(savedCoupon);
    }

    public List<DiscountCouponResponseDTO> getAllCoupons() {
        List<DiscountCoupon> coupons = discountCouponRepository.findAll();
        return coupons.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public DiscountCouponResponseDTO getCouponById(Long id) {
        DiscountCoupon coupon = discountCouponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount coupon not found with id: " + id));
        return mapToDTO(coupon);
    }

    public DiscountCouponResponseDTO updateCoupon(Long id, DiscountCouponDTO couponDTO) {
        Optional<DiscountCoupon> optionalExistingCoupon = discountCouponRepository.findById(id);
        if (optionalExistingCoupon.isEmpty()) {
            throw new RuntimeException("Discount coupon not found with id: " + id);
        }
        DiscountCoupon coupon = optionalExistingCoupon.get();

        // Update customers if provided and scope is selected
        Set<Customer> customers = new HashSet<>();
        if("selected".equalsIgnoreCase(couponDTO.getCustomerScope()) && !couponDTO.getCustomerIds().isEmpty()){
            customers = couponDTO.getCustomerIds().stream()
                    .map(customerId -> customerRepository.findById(customerId)
                        .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId)))
                    .collect(Collectors.toSet());
        } else if ("all".equalsIgnoreCase(couponDTO.getCustomerScope())) {
            customers = null;
        }


        coupon.setTitle(couponDTO.getTitle());
        coupon.setCouponCode(couponDTO.getCouponCode());
        coupon.setDiscountType(Enum.valueOf(com.monkhub.techtest.enums.DiscountType.class, couponDTO.getDiscountType()));
        coupon.setDiscountValue(couponDTO.getDiscountValue());
        coupon.setExpiryDate(couponDTO.getExpiryDate());
        coupon.setCustomerScope(couponDTO.getCustomerScope());
        coupon.setApplicableCustomers(customers);
        coupon.setMinimumDiscount(couponDTO.getMinimumDiscount());
        coupon.setMaximumDiscount(couponDTO.getMaximumDiscount());
        coupon.setUsagePerCustomer(Enum.valueOf(com.monkhub.techtest.enums.UsageLimit.class, couponDTO.getUsagePerCustomer()));
        coupon.setUsageLimit(couponDTO.getUsageLimit());


        DiscountCoupon updatedCoupon = discountCouponRepository.save(coupon);
        return mapToDTO(updatedCoupon);
    }

    public void deleteCoupon(Long id) {
        if (!discountCouponRepository.existsById(id)) {
            throw new RuntimeException("Discount coupon not found with id: " + id);
        }
        discountCouponRepository.deleteById(id);
    }

    private DiscountCouponResponseDTO mapToDTO(DiscountCoupon coupon) {
        Set<Customer> customers = new HashSet<>();
        if ("selected".equalsIgnoreCase(coupon.getCustomerScope()) && !coupon.getApplicableCustomers().isEmpty()) {
            customers = new HashSet<>(coupon.getApplicableCustomers());
            customers.stream()
                    .forEach(customer -> customer.getCoupons().clear());
        } else if ("all".equalsIgnoreCase(coupon.getCustomerScope())) {
            customers = null;
        }
        return DiscountCouponResponseDTO.builder()
                .title(coupon.getTitle())
                .couponCode(coupon.getCouponCode())
                .discountType(coupon.getDiscountType().name())
                .discountValue(coupon.getDiscountValue())
                .expiryDate(coupon.getExpiryDate())
                .customerScope(coupon.getCustomerScope())
                .customers(customers)
                .minimumDiscount(coupon.getMinimumDiscount())
                .maximumDiscount(coupon.getMaximumDiscount())
                .usagePerCustomer(coupon.getUsagePerCustomer().name())
                .usageLimit(coupon.getUsageLimit())
                .build();
    }

    private DiscountCoupon mapToEntity(DiscountCouponDTO couponDTO) {
        Set<Customer> customers = new HashSet<>();
        if ("selected".equalsIgnoreCase(couponDTO.getCustomerScope()) && !couponDTO.getCustomerIds().isEmpty()) {
            customers = couponDTO.getCustomerIds().stream()
                    .map(customerId -> customerRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId)))
                    .collect(Collectors.toSet());

        } else if ("all".equalsIgnoreCase(couponDTO.getCustomerScope())) {
            customers = null;
        }
        return DiscountCoupon.builder()
                .title(couponDTO.getTitle())
                .couponCode(couponDTO.getCouponCode())
                .discountType(Enum.valueOf(com.monkhub.techtest.enums.DiscountType.class, couponDTO.getDiscountType()))
                .discountValue(couponDTO.getDiscountValue())
                .expiryDate(couponDTO.getExpiryDate())
                .customerScope(couponDTO.getCustomerScope())
                .applicableCustomers(customers)
                .minimumDiscount(couponDTO.getMinimumDiscount())
                .maximumDiscount(couponDTO.getMaximumDiscount())
                .usagePerCustomer(Enum.valueOf(com.monkhub.techtest.enums.UsageLimit.class, couponDTO.getUsagePerCustomer()))
                .usageLimit(couponDTO.getUsageLimit())
                .build();
    }
}
