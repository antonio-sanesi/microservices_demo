package it.asanesi.product_service.service.impl;

import it.asanesi.product_service.dto.ProductDTO;
import it.asanesi.product_service.entity.ProductEntity;
import it.asanesi.product_service.repository.ProductRepository;
import it.asanesi.product_service.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id)
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .orElse(null);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        var productToSave = modelMapper.map(productDTO, ProductEntity.class);
        var productSaved = productRepository.save(productToSave);
        return modelMapper.map(productSaved, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            productDTO.setId(id);
        } else if (!productDTO.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in body and path do not match");
        }
        var productToSave = modelMapper.map(productDTO, ProductEntity.class);
        var productSaved = productRepository.save(productToSave);
        return modelMapper.map(productSaved, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);

    }

    @Override
    public void updateStock(Long id, Integer quantity) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        var newStock = product.getStock() + quantity;

        if (newStock < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock cannot be negative");
        }

        product.setStock(newStock);
        productRepository.save(product);
    }
}
