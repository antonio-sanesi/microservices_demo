package it.asanesi.product_service.service;

import it.asanesi.product_service.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();

    ProductDTO getProduct(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    void updateStock(Long id, Integer quantity);
}
