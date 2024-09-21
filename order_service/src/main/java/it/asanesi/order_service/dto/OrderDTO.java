package it.asanesi.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal price;
}

