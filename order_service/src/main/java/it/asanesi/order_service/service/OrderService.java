package it.asanesi.order_service.service;

import it.asanesi.order_service.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getOrders();

    OrderDTO getOrder(Long id);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);
}
