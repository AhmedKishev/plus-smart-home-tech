package ru.yandex.practicum.delivery.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.api.client.OrderClient;
import ru.yandex.practicum.api.client.WarehouseClient;
import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.enums.DeliveryState;
import ru.yandex.practicum.api.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.delivery.exception.NoDeliveryFoundException;
import ru.yandex.practicum.delivery.mapper.DeliveryMapper;
import ru.yandex.practicum.delivery.model.Delivery;
import ru.yandex.practicum.delivery.repository.DeliveryRepository;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    DeliveryRepository deliveryRepository;
    WarehouseClient warehouseClient;
    OrderClient orderClient;


    String ADDRESS1 = "ADDRESS_1";
    String ADDRESS2 = "ADDRESS_2";

    @Override
    public Double deliveryCost(OrderDto orderDto) {
        Double baseCost = 5.0;

        Delivery deliveryById = deliveryRepository.findByOrderId(orderDto.getOrderId())
                .orElseThrow(() -> new NoDeliveryFoundException(String.format("Не найден заказ c id: %s", orderDto.getOrderId())));

        if (deliveryById.getFromAddress().getCity().contains(ADDRESS1)) {
            baseCost *= 1;
        } else if (deliveryById.getFromAddress().getCity().contains(ADDRESS2)) {
            Double tmp = baseCost * 2;
            baseCost += tmp;
        }

        if (orderDto.getFragile()) {
            Double tmp = baseCost * 0.2;
            baseCost += tmp;
        }

        baseCost += orderDto.getDeliveryWeight() * 0.3;
        baseCost += orderDto.getDeliveryVolume() * 0.2;

        if (!deliveryById.getFromAddress().getStreet().equals(deliveryById.getToAddress().getStreet())) {
            baseCost += baseCost * 0.2;
        }
        return baseCost;
    }

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = DeliveryMapper.toDelivery(deliveryDto);
        return DeliveryMapper.toDeliveryDto(deliveryRepository.save(delivery));
    }

    @Override
    public void successfulDelivery(UUID deliveryId) {
        Delivery findByIdDelivery = getDeliveryById(deliveryId);

        findByIdDelivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.orderDelivery(findByIdDelivery.getOrderId());
    }

    @Override
    public void pickedDelivery(UUID deliveryId) {
        Delivery findByIdDelivery = getDeliveryById(deliveryId);

        findByIdDelivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        orderClient.assemblyOrder(findByIdDelivery.getOrderId());
        warehouseClient.shippedOrder(new ShippedToDeliveryRequest(findByIdDelivery.getOrderId(), findByIdDelivery.getDeliveryId()));
    }

    @Override
    public void failedDelivery(UUID deliveryId) {
        Delivery findByIdDelivery = getDeliveryById(deliveryId);

        findByIdDelivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.orderDeliveryFailed(findByIdDelivery.getOrderId());
    }

    private Delivery getDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NoDeliveryFoundException(String.format("Доставка с id: %s не зарегистрирована", deliveryId)));
    }
}
