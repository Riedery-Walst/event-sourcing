package ru.andreev.ufanet.service;

import ru.andreev.ufanet.event.Event;
import ru.andreev.ufanet.model.Order;

import java.util.UUID;

public interface OrderService {
    void publishEvent(Event event, UUID orderId);

    void saveEvent(Event event);

    Order findOrder(UUID id);
}
