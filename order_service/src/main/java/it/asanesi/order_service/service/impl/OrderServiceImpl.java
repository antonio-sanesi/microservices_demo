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

        //salvo l'ordine
        var orderToSave = modelMapper.map(orderDTO, OrderEntity.class);
        var orderSaved = orderRepository.save(orderToSave);

        productServiceClient.updateStock(orderDTO.getProduct().getId(), -orderDTO.getQuantity());

        return modelMapper.map(orderSaved, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
