package ru.andreev.ufanet.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.andreev.ufanet.model.Order;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderProcessedEvent extends Event {
    private UUID orderId;
    private int employeeId;
}
