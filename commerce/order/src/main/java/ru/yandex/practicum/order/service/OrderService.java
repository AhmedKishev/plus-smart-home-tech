package ru.yandex.practicum.order.service;

import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;
import ru.yandex.practicum.api.request.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createNewOrder(CreateNewOrderRequest createNewOrderRequest);

    List<OrderDto> getAllOrderByUsername(String username);

    OrderDto getOrder(ProductReturnRequest productReturnRequest);

    OrderDto paymentOrder(UUID orderId);

    OrderDto paymentFailed(UUID orderId);

    OrderDto deliveryOrder(UUID orderId);

    OrderDto deliveryOrderFailed(UUID orderId);

    OrderDto completedOrder(UUID orderId);

    OrderDto calculateTotalCostOrder(UUID orderId);

    OrderDto calculateDeliveryCost(UUID orderId);

    OrderDto assemblyOrder(UUID orderId);

    OrderDto assemblyOrderFailed(UUID orderId);
}
