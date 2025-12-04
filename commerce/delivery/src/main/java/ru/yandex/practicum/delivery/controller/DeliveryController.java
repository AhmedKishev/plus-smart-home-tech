package ru.yandex.practicum.delivery.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.delivery.service.DeliveryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeliveryController {

    DeliveryService deliveryService;


    @PostMapping("/cost")
    Double deliveryCost(@RequestBody OrderDto orderDto) {
        return deliveryService.deliveryCost(orderDto);
    }


    @PutMapping
    DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    @PostMapping("/successful")
    void successfulDelivery(@RequestBody UUID deliveryId) {
        deliveryService.successfulDelivery(deliveryId);
    }


    @PostMapping("/picked")
    void pickedDelivery(@RequestBody UUID deliveryId) {
        deliveryService.pickedDelivery(deliveryId);
    }

    @PostMapping("/failed")
    void failedDelivery(@RequestBody UUID deliveryId) {
        deliveryService.failedDelivery(deliveryId);
    }

}
