package ru.yandex.practicum.delivery.service;

import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;

import java.util.UUID;

public interface DeliveryService {
    Double deliveryCost(OrderDto orderDto);

    DeliveryDto createDelivery(DeliveryDto deliveryDto);

    void successfulDelivery(UUID deliveryId);

    void pickedDelivery(UUID deliveryId);

    void failedDelivery(UUID deliveryId);
}
