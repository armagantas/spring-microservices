package com.armagantas.ecommerce.payment;

import com.armagantas.ecommerce.customer.Customer;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        
        BigDecimal amount,
        
        PaymentMethod paymentMethod,
        
        Integer orderId,
        
        String orderReference,
        
        Customer customer
) {
}
