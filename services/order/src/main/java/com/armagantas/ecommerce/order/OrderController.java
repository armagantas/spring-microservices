package com.armagantas.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService _orderService;
    
    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(_orderService.createOrder(request));
    }
    
    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(_orderService.findAllOrders());
    }
    
    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(
            @PathVariable("order-id") Integer orderId
    ) {
        return ResponseEntity.ok(_orderService.findOrderById(orderId));
    }
}
