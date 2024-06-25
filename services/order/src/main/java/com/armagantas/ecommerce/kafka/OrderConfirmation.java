package com.armagantas.ecommerce.kafka;

import com.armagantas.ecommerce.customer.CustomerResponse;
import com.armagantas.ecommerce.order.PaymentMethod;
import com.armagantas.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> product
) {
}
