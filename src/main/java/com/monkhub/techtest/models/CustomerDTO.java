package com.monkhub.techtest.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    @Size(max = 150, message = "Email must be at most 150 characters")
    private String email;

    @NotBlank(message = "Mobile must not be blank")
    @Size(max = 15, message = "Mobile must be at most 15 characters")
    private String mobile;

    @Builder.Default
    private Set<Long> couponIds = null;
}
