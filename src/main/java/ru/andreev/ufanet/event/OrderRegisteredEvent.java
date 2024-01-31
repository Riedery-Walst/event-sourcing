package ru.andreev.ufanet.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderRegisteredEvent extends Event {
    private UUID orderId;
    private int employeeId;
    private int customerId;
    private String deliveryTimeLeft;
    private int productId;
    private BigDecimal price;
}
