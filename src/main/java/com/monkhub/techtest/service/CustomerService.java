package com.monkhub.techtest.service;

import com.monkhub.techtest.entities.Customer;
import com.monkhub.techtest.entities.DiscountCoupon;
import com.monkhub.techtest.models.CustomerDTO;
import com.monkhub.techtest.repository.CustomerRepository;
import com.monkhub.techtest.repository.DiscountCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

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
        Customer customer = mapToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return mapToDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> optionalExistingCustomer = customerRepository.findById(id);
        if (optionalExistingCustomer.isEmpty()) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        Customer customer = optionalExistingCustomer.get();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobile(customerDTO.getMobile());

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToDTO(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        Set<Long> couponIds = null;
        if (customer.getCoupons() != null) {
            couponIds = customer.getCoupons().stream()
                    .map(DiscountCoupon::getId)
                    .collect(Collectors.toSet());
        }
        return CustomerDTO.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .couponIds(couponIds)
                .build();
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {

        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .mobile(customerDTO.getMobile())
                .coupons(null)
                .build();
    }
}
