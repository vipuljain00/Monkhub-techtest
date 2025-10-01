package com.monkhub.techtest.entities;

import com.monkhub.techtest.enums.DiscountType;
import com.monkhub.techtest.enums.UsageLimit;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "discount_coupon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String couponCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Double discountValue;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private String customerScope; // 'all' or 'selected'

    @ManyToMany
    @JoinTable(
        name = "customer_discount_coupon",
        joinColumns = @JoinColumn(name = "discount_coupon_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> applicableCustomers;

    private Double minimumDiscount;

    private Double maximumDiscount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsageLimit usagePerCustomer; // 'Unlimited' or fixed number as string

    private Integer usageLimit; // in case fixed number is needed
}
