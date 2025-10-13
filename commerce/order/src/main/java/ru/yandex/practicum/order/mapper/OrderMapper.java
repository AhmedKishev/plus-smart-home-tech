package ru.yandex.practicum.order.mapper;

import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.order.model.Order;

public class OrderMapper {


    public static OrderDto toOrderDto(Order newOrder) {
        return OrderDto.builder()
                .orderId(newOrder.getOrderId())
                .shoppingCartId(newOrder.getShoppingCartId())
                .products(newOrder.getProducts())
                .paymentId(newOrder.getPaymentId())
                .deliveryId(newOrder.getDeliveryId())
                .state(newOrder.getState())
                .deliveryWeight(newOrder.getDeliveryWeight())
                .deliveryVolume(newOrder.getDeliveryVolume())
                .fragile(newOrder.isFragile())
                .totalPrice(newOrder.getTotalPrice())
                .deliveryPrice(newOrder.getDeliveryPrice())
                .productPrice(newOrder.getProductPrice())
                .build();
    }
}
