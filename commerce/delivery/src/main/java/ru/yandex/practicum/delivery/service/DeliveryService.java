package ru.yandex.practicum.delivery.service;

import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;

public interface DeliveryService {
    Double deliveryCost(OrderDto orderDto);

    DeliveryDto createDelivery(DeliveryDto deliveryDto);
}
