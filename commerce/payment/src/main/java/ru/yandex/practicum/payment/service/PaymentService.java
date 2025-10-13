package ru.yandex.practicum.payment.service;

import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.PaymentDto;

public interface PaymentService {
    Double checkProductCost(OrderDto orderDto);

    PaymentDto createPayment(OrderDto orderDto);
}
