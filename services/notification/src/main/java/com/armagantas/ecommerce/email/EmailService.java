package com.armagantas.ecommerce.email;

import com.armagantas.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.armagantas.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.armagantas.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    
    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("armagantas6@gmail.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("amount", amount);
        model.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getTemplate());
        
        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info("Email sent successfully");
        } catch (MessagingException e) {
            log.warn("Warning- Cannot send email to {}", destinationEmail);
        }
    }

    @Async
    public void sendOrderSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("armagantas6@gmail.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("totalAmount", amount);
        model.put("orderReference", orderReference);
        model.put("products", products);


        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(ORDER_CONFIRMATION.getTemplate());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info("Email sent successfully");
        } catch (MessagingException e) {
            log.warn("Warning- Cannot send email to {}", destinationEmail);
        }
    }
}
