package com.armagantas.ecommerce.orderline;

import com.armagantas.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLines toOrderLine(OrderLineRequest request) {
        return OrderLines.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .productId(request.productId())
                .build();
    }
}
