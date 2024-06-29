package com.armagantas.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation.");
    
    @Getter
    private final String template;
    
    @Getter
    private final String title;
    
    EmailTemplates(String template, String title) {
        this.template = template;
        this.title = title;
    }
}
