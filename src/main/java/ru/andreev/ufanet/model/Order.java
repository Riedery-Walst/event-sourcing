package ru.andreev.ufanet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import ru.andreev.ufanet.event.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Order {
    private UUID orderId;
    private int customerId;
    private int employeeId;
    private String deliveryTimeLeft;
    private String cancelDescription;
    private int productId;
    private BigDecimal price;
    private List<Event> events = new ArrayList<>();
    private String orderStatus;
    private LocalDateTime orderTimeCreation;

    public void apply(OrderRegisteredEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getEventType();
        this.customerId = event.getCustomerId();
        this.deliveryTimeLeft = event.getDeliveryTimeLeft();
        this.productId = event.getProductId();
        this.price = event.getPrice();

    }

    public void apply(OrderDeliveredEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getEventType();
        this.employeeId = event.getEmployeeId();
    }

    public void apply(OrderProcessedEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getEventType();
        this.employeeId = event.getEmployeeId();
    }

    public void apply(OrderReadyEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getEventType();
        this.employeeId = event.getEmployeeId();
    }

    public void apply(OrderCanceledEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getEventType();
        this.employeeId = event.getEmployeeId();
        this.cancelDescription = event.getCancelDescription();
    }
}
