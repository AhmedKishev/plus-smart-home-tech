package ru.yandex.practicum.delivery.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.delivery.service.DeliveryService;

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

}
