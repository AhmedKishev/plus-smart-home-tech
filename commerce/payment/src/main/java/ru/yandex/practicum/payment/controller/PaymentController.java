package ru.yandex.practicum.payment.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.PaymentDto;
import ru.yandex.practicum.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentController {
    PaymentService paymentService;

    @PostMapping("/productCost")
    Double productCost(@RequestBody OrderDto orderDto) {
        return paymentService.checkProductCost(orderDto);
    }


    @PostMapping
    PaymentDto createPayment(@RequestBody OrderDto orderDto) {
        return paymentService.createPayment(orderDto);
    }


}
