package com.armagantas.ecommerce.notification;

import com.armagantas.ecommerce.kafka.order.OrderConfirmation;
import com.armagantas.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Id
    private String id;
    
    private NotificationType type;
    
    private LocalDateTime notificationTime;
    
    private OrderConfirmation orderConfirmation;
    
    private PaymentConfirmation paymentConfirmation;
}
