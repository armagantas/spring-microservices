package com.armagantas.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService _customerService;
    
    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
       return ResponseEntity.ok(_customerService.createCustomer(request));
    }
    
    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        _customerService.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }  
    
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(_customerService.findAllCustomers());
    }
    
    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(_customerService.existsById(customerId));
    }
    
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(_customerService.findById(customerId));
    }
    
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomerById(
            @PathVariable("customer-id") String customerId
    ) {
        _customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
