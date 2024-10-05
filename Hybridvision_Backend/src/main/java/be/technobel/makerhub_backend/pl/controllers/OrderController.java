package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.bll.services.OrderService;
import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import be.technobel.makerhub_backend.pl.models.dtos.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getUserOrders(@RequestParam String username){
        List<OrderDto> orders = orderService.findOrdersByUsername(username)
                .stream()
                .map(OrderDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
