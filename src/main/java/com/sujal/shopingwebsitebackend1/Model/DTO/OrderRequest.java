package com.sujal.shopingwebsitebackend1.Model.DTO;

import java.util.List;

public record OrderRequest(
        String customerName,
        String email,
        List<OrderItemRequest> items

) {
}
