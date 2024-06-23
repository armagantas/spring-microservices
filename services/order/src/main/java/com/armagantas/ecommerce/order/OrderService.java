package com.armagantas.ecommerce.order;

import com.armagantas.ecommerce.customer.CustomerClient;
import com.armagantas.ecommerce.customer.CustomerResponse;
import com.armagantas.ecommerce.exceptions.BusinessException;
import com.armagantas.ecommerce.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    
    public Integer createOrder(OrderRequest request) {
        // checking customer using openfeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Create have not been created due to customer have not been found."));
        
        // purchase the product from product-microservice we'll use rest template
        
        
        // persist order
        
        
        // persist order lines
        
        
        // start payment process
        
        
        // send the order confirmation to notification-microservice (kafka)
        return null;
    }   
}
