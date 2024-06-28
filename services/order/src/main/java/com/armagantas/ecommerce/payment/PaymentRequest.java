package com.armagantas.ecommerce.payment;

import com.armagantas.ecommerce.customer.CustomerResponse;
import com.armagantas.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        CustomerResponse customer
) {
}
