package com.sujal.shopingwebsitebackend1.Controller;

import com.sujal.shopingwebsitebackend1.Model.DTO.OrderRequest;
import com.sujal.shopingwebsitebackend1.Model.DTO.OrderResponse;
import com.sujal.shopingwebsitebackend1.Service.Orderservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {
    @Autowired
    private Orderservice orderservice;
    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeorder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderservice.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        List<OrderResponse> responses;
        responses = orderservice.getordersresponses();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }
}
