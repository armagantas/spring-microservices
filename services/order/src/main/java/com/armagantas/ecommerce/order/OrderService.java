package com.armagantas.ecommerce.order;

import com.armagantas.ecommerce.customer.CustomerClient;
import com.armagantas.ecommerce.customer.CustomerResponse;
import com.armagantas.ecommerce.exceptions.BusinessException;
import com.armagantas.ecommerce.kafka.OrderConfirmation;
import com.armagantas.ecommerce.kafka.OrderProducer;
import com.armagantas.ecommerce.orderline.OrderLineRequest;
import com.armagantas.ecommerce.orderline.OrderLineService;
import com.armagantas.ecommerce.payment.PaymentClient;
import com.armagantas.ecommerce.payment.PaymentRequest;
import com.armagantas.ecommerce.product.ProductClient;
import com.armagantas.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        // checking customer using openfeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Create have not been created due to customer have not been found."));
        
        // purchase the product from product-microservice we'll use rest template
        var purchasedProduct = this.productClient.purchaseProducts(request.products());
        
        // persist order
        var order = this.repository.save(orderMapper.toOrder(request));
        
        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        
        // start payment process
        var paymentRequest = new PaymentRequest(
          request.amount(),
          request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        
        
        // send the order confirmation to notification-microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(orderMapper :: fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(Integer orderId) {
        return repository.findById(orderId)
                .map(orderMapper :: fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found."));
    }
}
