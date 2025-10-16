package ru.yandex.practicum.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;
import ru.yandex.practicum.api.request.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order",path = "/api/v1/order")
public interface OrderClient {
    @PutMapping
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequest createNewOrderRequest);

    @GetMapping
    List<OrderDto> getAllOrderByUser(@RequestBody String username);


    @PostMapping("/return")
    OrderDto getOrder(@RequestBody ProductReturnRequest productReturnRequest);


    @PostMapping("/payment")
    OrderDto paymentOrder(@RequestBody UUID orderId);

    @PostMapping("/payment/failed")
    OrderDto paymentFailed(@RequestBody UUID orderId);


    @PostMapping("/delivery")
    OrderDto orderDelivery(@RequestBody UUID orderId);

    @PostMapping("/delivery/failed")
    OrderDto orderDeliveryFailed(@RequestBody UUID orderId);


    @PostMapping("/completed")
    OrderDto orderCompleted(@RequestBody UUID orderId);


    @PostMapping("/calculate/total")
    OrderDto calculateTotalCostOrder(@RequestBody UUID orderId);

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryCost(@RequestBody UUID orderId);

    @PostMapping("/assembly")
    OrderDto assemblyOrder(@RequestBody UUID orderId);

    @PostMapping("/assembly/failed")
    OrderDto assemblyOrderFailed(@RequestBody UUID orderId);

}
