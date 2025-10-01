package com.monkhub.techtest.entities;


import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    private String mobile;

    @ManyToMany(mappedBy = "applicableCustomers")
    @JsonIgnore
    private Set<DiscountCoupon> coupons;
}

