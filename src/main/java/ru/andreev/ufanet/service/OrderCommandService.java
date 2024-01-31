package ru.andreev.ufanet.service;

import org.springframework.stereotype.Service;
import ru.andreev.ufanet.command.*;
import ru.andreev.ufanet.event.*;

import java.util.UUID;

@Service
public class OrderCommandService {
    private final OrderService orderService;

    public OrderCommandService(OrderServiceImp orderService) {
        this.orderService = orderService;
    }

    public void handleOrderRegisterCommand(OrderRegisterCommand command) {
        OrderRegisteredEvent event = new OrderRegisteredEvent(UUID.randomUUID(), command.getEmployeeId(),
                command.getCustomerId(), command.getDeliveryTimeLeft(),
                command.getProductId(), command.getPrice());

        orderService.publishEvent(event, event.getOrderId());
    }

    public void handleOrderDeliverCommand(OrderDeliverCommand command) {
        OrderDeliveredEvent event = new OrderDeliveredEvent(command.getOrderId(),
                command.getEmployeeId());

        orderService.publishEvent(event, event.getOrderId());
    }

    public void handleOrderCancelCommand(OrderCancelCommand command) {
        OrderCanceledEvent event = new OrderCanceledEvent(command.getOrderId(),
                command.getEmployeeId(), command.getCancelDescription());

        orderService.publishEvent(event, event.getOrderId());
    }

    public void handleOrderReadyCommand(OrderReadyCommand command) {
        OrderReadyEvent event = new OrderReadyEvent(command.getOrderId(),
                command.getEmployeeId());

        orderService.publishEvent(event, event.getOrderId());
    }

    public void handleOrderProcessCommand(OrderProcessCommand command) {
        OrderProcessedEvent event = new OrderProcessedEvent(command.getOrderId(),
                command.getEmployeeId());

        orderService.publishEvent(event, event.getOrderId());
    }
}
