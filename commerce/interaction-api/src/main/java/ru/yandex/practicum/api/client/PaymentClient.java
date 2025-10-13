package ru.yandex.practicum.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.PaymentDto;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient {


    @PostMapping("/productCost")
    Double productCost(@RequestBody OrderDto orderDto);


    @PostMapping
    PaymentDto createPayment(@RequestBody OrderDto orderDto);

}
