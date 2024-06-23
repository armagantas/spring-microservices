package com.armagantas.ecommerce.orderline;

import com.armagantas.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLines {
    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    private Integer productId;
    
    private double quantity;
}
