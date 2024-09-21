package it.asanesi.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stock;
}
