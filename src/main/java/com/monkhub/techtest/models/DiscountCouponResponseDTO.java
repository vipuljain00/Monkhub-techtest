package com.monkhub.techtest.models;

import com.monkhub.techtest.entities.Customer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCouponResponseDTO {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @NotBlank(message = "Coupon code must not be blank")
    @Size(max = 50, message = "Coupon code must be at most 50 characters")
    private String couponCode;

    @NotNull(message = "Discount type is required")
    @Pattern(regexp = "FIXED|PERCENTAGE", message = "Discount type must be FIXED or PERCENTAGE")
    private String discountType;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be > 0")
    private Double discountValue;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    @NotBlank(message = "Customer scope is required")
    @Pattern(regexp = "all|selected", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Customer scope must be 'all' or 'selected'")
    private String customerScope;

    @Builder.Default
    private Set<Customer> customers = new HashSet<>();

    @DecimalMin(value = "0.0", message = "Minimum discount cannot be negative")
    private Double minimumDiscount;

    @DecimalMin(value = "0.0", message = "Maximum discount cannot be negative")
    private Double maximumDiscount;

    @NotNull(message = "Usage per customer is required")
    @Pattern(regexp = "UNLIMITED|FIXED", message = "Usage per customer must be UNLIMITED or FIXED")
    private String usagePerCustomer;

    @Min(value = 1, message = "Usage limit must be >= 1, if usage per customer is FIXED")
    private Integer usageLimit;
}
