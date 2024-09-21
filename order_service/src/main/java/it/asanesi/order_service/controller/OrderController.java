package it.asanesi.order_service.controller;

import it.asanesi.order_service.dto.OrderDTO;
import it.asanesi.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}