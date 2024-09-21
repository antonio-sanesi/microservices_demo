package it.asanesi.product_service.controller;

import it.asanesi.product_service.dto.ProductDTO;
import it.asanesi.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //crud methods with @GetMapping, @PostMapping, and so on
    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}/stock")
    public void updateStock(@PathVariable Long id, @RequestBody Integer quantity) {
        productService.updateStock(id, quantity);
    }
}
