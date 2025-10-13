package ru.yandex.practicum.order.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;
import ru.yandex.practicum.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderController {

    OrderService orderService;


    @PutMapping
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequest createNewOrderRequest) {
       return orderService.createNewOrder(createNewOrderRequest);
    }


}
