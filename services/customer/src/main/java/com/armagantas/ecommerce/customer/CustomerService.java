package com.armagantas.ecommerce.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository _customerRepository;
    
    private final CustomerMapper _customerMapper;
    
    public String createCustomer(CustomerRequest request) {
        var customer = _customerRepository.save(_customerMapper.toCustomer(request));
        return customer.getId();
    }
}
