package ru.yandex.practicum.delivery.mapper;

import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.enums.DeliveryState;
import ru.yandex.practicum.delivery.model.Delivery;

public class DeliveryMapper {

    public static Delivery toDelivery(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .deliveryState(DeliveryState.CREATED)
                .fromAddress(AddressMapper.toAddress(deliveryDto.getFromAddress()))
                .toAddress(AddressMapper.toAddress(deliveryDto.getToAddress()))
                .orderId(deliveryDto.getOrderId())
                .build();
    }

    public static DeliveryDto toDeliveryDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .state(delivery.getDeliveryState())
                .toAddress(AddressMapper.toAddressDto(delivery.getToAddress()))
                .fromAddress(AddressMapper.toAddressDto(delivery.getFromAddress()))
                .orderId(delivery.getOrderId())
                .build();
    }
}
