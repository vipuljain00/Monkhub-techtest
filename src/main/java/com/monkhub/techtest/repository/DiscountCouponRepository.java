package com.monkhub.techtest.repository;

import com.monkhub.techtest.entities.DiscountCoupon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountCouponRepository extends JpaRepository <DiscountCoupon, Long>{

    @Query("SELECT c FROM DiscountCoupon c")
    @EntityGraph(attributePaths = "applicableCustomers")
    List<DiscountCoupon> findAllWithCustomers();

    @Query("SELECT c FROM DiscountCoupon c WHERE c.id = :id")
    @EntityGraph(attributePaths = "applicableCustomers")
    Optional<DiscountCoupon> findByIdWithCustomers(@Param("id") Long id);
}
