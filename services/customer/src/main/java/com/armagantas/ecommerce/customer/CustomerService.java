package com.armagantas.ecommerce.customer;

import com.armagantas.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository _customerRepository;

    private final CustomerMapper _customerMapper;

    public String createCustomer(CustomerRequest request) {
        var customer = _customerRepository.save(_customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = _customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Customer with id %s not found", request.id())
                ));
        mergerCustomer(customer, request);
        _customerRepository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }

        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }

        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }

        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return _customerRepository.findAll()
                .stream()
                .map(_customerMapper :: fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return _customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return _customerRepository.findById(customerId)
                .map(_customerMapper ::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with id %s not found", customerId)));
    }

    public void deleteCustomer(String customerId) {
        _customerRepository.deleteById(customerId);
    }
}
