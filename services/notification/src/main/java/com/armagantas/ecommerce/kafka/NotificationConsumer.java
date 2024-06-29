package com.armagantas.ecommerce.kafka;

import com.armagantas.ecommerce.kafka.order.OrderConfirmation;
import com.armagantas.ecommerce.kafka.payment.PaymentConfirmation;
import com.armagantas.ecommerce.notification.Notification;
import com.armagantas.ecommerce.notification.NotificationRepository;
import com.armagantas.ecommerce.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.armagantas.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static com.armagantas.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    //private final EmailService emailService;
    
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) {
        log.info(format("Consumed payment confirmation: %s", paymentConfirmation));
        
        notificationRepository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationTime(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
        
        //todo send an email
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) {
        log.info(format("Consumed order confirmation: %s", orderConfirmation));

        notificationRepository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationTime(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        //todo send an email
    }
}
