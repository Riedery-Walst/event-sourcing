package ru.andreev.ufanet.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.andreev.ufanet.adapter.LocalDateTimeTypeAdapter;
import ru.andreev.ufanet.event.*;
import ru.andreev.ufanet.exception.OrderIsDeliveredOrCanceledException;
import ru.andreev.ufanet.exception.OrderIsNotRegisteredException;
import ru.andreev.ufanet.exception.OrderNotFountException;
import ru.andreev.ufanet.model.Order;
import ru.andreev.ufanet.repository.EventRepository;
import ru.andreev.ufanet.strategy.SuperClassExclusionStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImp implements OrderService {
    private final EventRepository eventRepository;

    public OrderServiceImp(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void publishEvent(Event event, UUID orderId) {
        Event eventStore = new Event();

        eventStore.setDateTime(LocalDateTime.now());
        eventStore.setEntityId(orderId);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .addSerializationExclusionStrategy(new SuperClassExclusionStrategy())
                .create();

        if (event instanceof OrderRegisteredEvent registeredEvent) {
            String eventDataJson = gson.toJson(registeredEvent);
            eventStore.setEventType("registered");
            eventStore.setEventData(eventDataJson);
        } else if (event instanceof OrderDeliveredEvent deliveredEvent) {
            String eventDataJson = gson.toJson(deliveredEvent);
            eventStore.setEventType("delivered");
            eventStore.setEventData(eventDataJson);
        } else if (event instanceof OrderCanceledEvent canceledEvent) {
            String eventDataJson = gson.toJson(canceledEvent);
            eventStore.setEventType("canceled");
            eventStore.setEventData(eventDataJson);
        } else if (event instanceof OrderProcessedEvent processedEvent) {
            String eventDataJson = gson.toJson(processedEvent);
            eventStore.setEventType("processed");
            eventStore.setEventData(eventDataJson);
        } else if (event instanceof OrderReadyEvent readyEvent) {
            String eventDataJson = gson.toJson(readyEvent);
            eventStore.setEventType("ready");
            eventStore.setEventData(eventDataJson);
        }

        saveEvent(eventStore);
    }

    @Override
    public void saveEvent(Event event) {
        List<Event> eventsList = new ArrayList<>(eventRepository.findByEntityIdOrderByDateTime(event.getEntityId()));
        String eventType = event.getEventType();

        if (eventType.equals("registered")) {
            eventRepository.save(event);
            log.info("Сохраняю " + event.getEventType());
        } else if (eventsList.isEmpty() || !eventsList.get(0).getEventType().equals("registered")) {
            throw new OrderIsNotRegisteredException("Заказ не был зарегистрирован");
        } else if (eventsList.get(eventsList.size() - 1).getEventType().equals("canceled")
                || eventsList.get(eventsList.size() - 1).getEventType().equals("delivered")) {
            throw new OrderIsDeliveredOrCanceledException("Заказ отменен или вручен");
        } else {
            eventRepository.save(event);
            log.info("Сохраняю " + event.getEventType());
        }
    }

    @Override
    public Order findOrder(UUID id) {
        List<Event> eventsList = new ArrayList<>(eventRepository.findByEntityIdOrderByDateTime(id));

        Order currentOrderState = new Order();

        if (!eventsList.isEmpty()) {
            for (Event event : eventsList) {
                switch (event.getEventType()) {
                    case "registered":
                        OrderRegisteredEvent registeredEvent = convertToOrderRegisteredEvent(event);
                        currentOrderState.apply(registeredEvent);
                        break;
                    case "delivered":
                        OrderDeliveredEvent deliveredEvent = convertToOrderDeliveredEvent(event);
                        currentOrderState.apply(deliveredEvent);
                        break;
                    case "canceled":
                        OrderCanceledEvent canceledEvent = convertToOrderCanceledEvent(event);
                        currentOrderState.apply(canceledEvent);
                        break;
                    case "processed":
                        OrderProcessedEvent processedEvent = convertToOrderProcessedEvent(event);
                        currentOrderState.apply(processedEvent);
                        break;
                    case "ready":
                        OrderReadyEvent readyEvent = convertToOrderReadyEvent(event);
                        currentOrderState.apply(readyEvent);
                        break;

                }
            }
            currentOrderState.setEvents(eventsList);
            currentOrderState.setOrderTimeCreation(eventsList.get(0).getDateTime());
            currentOrderState.setOrderStatus(eventsList.get(eventsList.size() - 1).getEventType());
        } else {
            throw new OrderNotFountException("Заказ не найден");
        }
        return currentOrderState;
    }

    private OrderRegisteredEvent convertToOrderRegisteredEvent(Event event) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        return gson.fromJson(event.getEventData(), OrderRegisteredEvent.class);
    }

    private OrderDeliveredEvent convertToOrderDeliveredEvent(Event event) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        return gson.fromJson(event.getEventData(), OrderDeliveredEvent.class);
    }

    private OrderCanceledEvent convertToOrderCanceledEvent(Event event) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        return gson.fromJson(event.getEventData(), OrderCanceledEvent.class);
    }

    private OrderProcessedEvent convertToOrderProcessedEvent(Event event) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        return gson.fromJson(event.getEventData(), OrderProcessedEvent.class);
    }

    private OrderReadyEvent convertToOrderReadyEvent(Event event) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        return gson.fromJson(event.getEventData(), OrderReadyEvent.class);
    }


}
