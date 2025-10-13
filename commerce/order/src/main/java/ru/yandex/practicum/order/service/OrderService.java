package ru.yandex.practicum.order.service;

import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;

public interface OrderService {
    OrderDto createNewOrder(CreateNewOrderRequest createNewOrderRequest);
}
