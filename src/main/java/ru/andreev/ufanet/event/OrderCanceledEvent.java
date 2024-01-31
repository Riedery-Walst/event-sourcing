package ru.andreev.ufanet.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderCanceledEvent extends Event {
    private UUID orderId;
    private int employeeId;
    private String cancelDescription;
}
