package com.sujal.shopingwebsitebackend1.Service;

import com.sujal.shopingwebsitebackend1.Model.DTO.OrderItemRequest;
import com.sujal.shopingwebsitebackend1.Model.DTO.OrderItemResponse;
import com.sujal.shopingwebsitebackend1.Model.DTO.OrderRequest;
import com.sujal.shopingwebsitebackend1.Model.DTO.OrderResponse;
import com.sujal.shopingwebsitebackend1.Model.Order;
import com.sujal.shopingwebsitebackend1.Model.OrderItem;
import com.sujal.shopingwebsitebackend1.Model.Product;
import com.sujal.shopingwebsitebackend1.Repo.orderRepo;
import com.sujal.shopingwebsitebackend1.Repo.productsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class Orderservice {
    @Autowired
    private productsRepo productsrepo;
    @Autowired
    private orderRepo orderRepo;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        String orderId = "ORD"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(orderRequest.customerName());
        order.setEmail(orderRequest.email());
        order.setStatus("placed");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequest items : orderRequest.items()){
            Product product = productsrepo.findById(items.productId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found"));
            product.setQuantity(product.getQuantity() - items.quantity());
            productsrepo.save(product);
            OrderItem orderitem = OrderItem.builder()
                    .product(product)
                    .quantity(items.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(items.quantity())))
                    .order(order)
                    .build();

            orderItems.add(orderitem);
        }
        order.setOrderItems(orderItems);
        orderRepo.save(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getordersresponses() {



        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orders){

            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for(OrderItem item : order.getOrderItems()){
                OrderItemResponse response =new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                orderItemResponses.add(response);
            }

            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    orderItemResponses

            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
