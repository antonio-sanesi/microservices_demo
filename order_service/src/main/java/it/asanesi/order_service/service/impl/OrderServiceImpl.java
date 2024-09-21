package it.asanesi.order_service.service.impl;

import it.asanesi.order_service.dto.OrderDTO;
import it.asanesi.order_service.entity.OrderEntity;
import it.asanesi.order_service.repository.OrderRepository;
import it.asanesi.order_service.service.OrderService;
import it.asanesi.order_service.dto.ProductDTO;
import it.asanesi.order_service.client.ProductServiceClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceClient productServiceClient;

    @Override
    public List<OrderDTO> getOrders() {
        return List.of();
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return null;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        if(orderDTO.getQuantity() <= 0) {
            log.error("Invalid quantity for order: {}", orderDTO.getQuantity());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid quantity for order");
        }

        var productDTO = orderDTO.getProduct();

        //ottengo il prodotto reale dal servizio di prodotti
        ProductDTO productReal;
        try {
            productReal = productServiceClient.getProductById(productDTO.getId());
            log.info("Fetched product details: {}", productReal);
        } catch (Exception e) {
            log.error("Error fetching product details for product ID: {}", productDTO.getId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error communicating with product service");
        }

        if(productReal.getStock() < orderDTO.getQuantity()) {
            log.error("Not enough stock for product ID: {}", productDTO.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock for product");
        }

        //calcolo il prezzo totale dell'ordine
        var calculatedPrice = productReal.getPrice().multiply(BigDecimal.valueOf(orderDTO.getQuantity()));

        //salvo l'ordine
        var orderToSave = new OrderEntity();
        orderToSave.setProductId(productDTO.getId());
        orderToSave.setQuantity(orderDTO.getQuantity());
        orderToSave.setPrice(calculatedPrice);

        var orderSaved = orderRepository.save(orderToSave);

        productServiceClient.updateStock(productDTO.getId(), -orderDTO.getQuantity());

        //creo l'oggetto di output per la risposta
        var outputDTO = new OrderDTO();
        outputDTO.setId(orderSaved.getId());
        outputDTO.setProduct(productReal);
        outputDTO.setQuantity(orderSaved.getQuantity());
        outputDTO.setPrice(orderSaved.getPrice());

        return outputDTO;
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
