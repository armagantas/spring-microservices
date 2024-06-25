package com.armagantas.ecommerce.orderline;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLines, Integer> {
    List<OrderLines> findAllByOrderId(Integer orderId);
}
