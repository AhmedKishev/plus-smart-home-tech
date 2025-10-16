package ru.yandex.practicum.payment.service;

import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.PaymentDto;

import java.util.UUID;

public interface PaymentService {
    Double checkProductCost(OrderDto orderDto);

    PaymentDto createPayment(OrderDto orderDto);

    Double calculateTotalCostOrder(OrderDto orderDto);

    void refundPayment(UUID paymentId);

    void failedPayment(UUID paymentId);
}
