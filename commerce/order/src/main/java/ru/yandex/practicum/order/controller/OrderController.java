package ru.yandex.practicum.order.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;
import ru.yandex.practicum.api.request.ProductReturnRequest;
import ru.yandex.practicum.order.service.OrderService;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    List<OrderDto> getAllOrderByUser(@RequestBody String username) {
        return orderService.getAllOrderByUsername(username);
    }


    @PostMapping("/return")
    OrderDto getOrder(@RequestBody ProductReturnRequest productReturnRequest) {
        return orderService.getOrder(productReturnRequest);
    }


    @PostMapping("/payment")
    OrderDto paymentOrder(@RequestBody UUID orderId) {
        return orderService.paymentOrder(orderId);
    }


    @PostMapping("/payment/failed")
    OrderDto paymentFailed(@RequestBody UUID orderId) {
        return orderService.paymentFailed(orderId);
    }


    @PostMapping("/delivery")
    OrderDto orderDelivery(@RequestBody UUID orderId) {
        return orderService.deliveryOrder(orderId);
    }

    @PostMapping("/delivery/failed")
    OrderDto orderDeliveryFailed(@RequestBody UUID orderId) {
        return orderService.deliveryOrderFailed(orderId);
    }


    @PostMapping("/completed")
    OrderDto orderCompleted(@RequestBody UUID orderId) {
        return orderService.completedOrder(orderId);
    }


    @PostMapping("/calculate/total")
    OrderDto calculateTotalCostOrder(@RequestBody UUID orderId) {
        return orderService.calculateTotalCostOrder(orderId);
    }

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryCost(@RequestBody UUID orderId) {
        return orderService.calculateDeliveryCost(orderId);
    }

    @PostMapping("/assembly")
    OrderDto assemblyOrder(@RequestBody UUID orderId) {
        return orderService.assemblyOrder(orderId);
    }

    @PostMapping("/assembly/failed")
    OrderDto assemblyOrderFailed(@RequestBody UUID orderId) {
        return orderService.assemblyOrderFailed(orderId);
    }

}
