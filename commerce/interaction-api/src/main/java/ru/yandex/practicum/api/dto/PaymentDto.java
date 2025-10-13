package ru.yandex.practicum.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class PaymentDto {

    UUID paymentId;

    Double totalPayment;

    Double deliveryPayment;

    Double freeTotal;

}
