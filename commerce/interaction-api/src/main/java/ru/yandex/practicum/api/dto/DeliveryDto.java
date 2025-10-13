package ru.yandex.practicum.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.api.enums.DeliveryState;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class DeliveryDto {

    UUID deliveryId;

    AddressDto fromAddress;

    AddressDto toAddress;

    UUID orderId;

    DeliveryState state;

}
