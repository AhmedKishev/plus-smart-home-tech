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

import java.util.UUID;

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

    @PostMapping("/totalCost")
    Double calculateTotalCostOrder(@RequestBody OrderDto orderDto) {
        return paymentService.calculateTotalCostOrder(orderDto);
    }

    @PostMapping("/refund")
    void refundPayment(@RequestBody UUID paymentId) {
        paymentService.refundPayment(paymentId);
    }

    @PostMapping("/failed")
    void failedPayment(@RequestBody UUID paymentId) {
        paymentService.failedPayment(paymentId);
    }
}
