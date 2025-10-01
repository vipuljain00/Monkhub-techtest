package com.monkhub.techtest.service;

import com.monkhub.techtest.entities.Customer;
import com.monkhub.techtest.entities.DiscountCoupon;
import com.monkhub.techtest.models.CustomerDTO;
import com.monkhub.techtest.repository.CustomerRepository;
import com.monkhub.techtest.repository.DiscountCouponRepository;
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
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final DiscountCouponRepository discountCouponRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.getCoupons().clear();
        Customer customer = mapToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }


    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }



    private CustomerDTO mapToDTO(Customer customer) {
        Set<DiscountCoupon> coupons = new HashSet<>();
        if (customer.getCoupons() != null) {
            coupons = new HashSet<>(customer.getCoupons());
            coupons.stream()
                    .forEach(coupon -> coupon.getApplicableCustomers().clear());
        }
        return CustomerDTO.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .coupons(coupons)
                .build();
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {

        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .mobile(customerDTO.getMobile())
                .build();
    }
}
