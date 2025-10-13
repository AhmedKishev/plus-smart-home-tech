package ru.yandex.practicum.payment.mapper;

import ru.yandex.practicum.api.dto.PaymentDto;
import ru.yandex.practicum.payment.model.Payment;

public class PaymentMapper {

    public static PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .deliveryPayment(payment.getDeliveryTotal())
                .paymentId(payment.getPaymentId())
                .totalPayment(payment.getTotalPayment())
                .freeTotal(payment.getFeeTotal())
                .build();
    }
}
