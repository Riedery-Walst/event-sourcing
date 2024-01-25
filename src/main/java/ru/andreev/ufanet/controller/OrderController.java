package ru.andreev.ufanet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.ufanet.command.*;
import ru.andreev.ufanet.model.Order;
import ru.andreev.ufanet.service.OrderCommandService;
import ru.andreev.ufanet.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping({"api/order"})
public class OrderController {
    private final OrderCommandService orderCommandService;
    private final OrderService orderService;

    public OrderController(OrderCommandService orderCommandService, OrderService orderService) {
        this.orderCommandService = orderCommandService;
        this.orderService = orderService;
    }


    @PostMapping("/register")
    public void registerOrder(@RequestBody OrderRegisterCommand command) throws JsonProcessingException {
        orderCommandService.handleOrderRegisterCommand(command);
    }

    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody OrderCancelCommand command) throws JsonProcessingException {
        orderCommandService.handleOrderCancelCommand(command);
    }

    @PostMapping("/process")
    public void processOrder(@RequestBody OrderProcessCommand command) throws JsonProcessingException {
        orderCommandService.handleOrderProcessCommand(command);
    }

    @PostMapping("/deliver")
    public void deliverOrder(@RequestBody OrderDeliverCommand command) throws JsonProcessingException {
        orderCommandService.handleOrderDeliverCommand(command);
    }

    @PostMapping("/ready")
    public void markOrderAsReady(@RequestBody OrderReadyCommand command) throws JsonProcessingException {
        orderCommandService.handleOrderReadyCommand(command);
    }

    @GetMapping({"/{orderId}"})
    public ResponseEntity<Order> getLastOrderState(@PathVariable UUID orderId) {
        Order lastOrderState = this.orderService.findOrder(orderId);
        return lastOrderState != null ? ResponseEntity.ok(lastOrderState) : ResponseEntity.notFound().build();
    }
}
