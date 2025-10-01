package ru.yandex.practicum.api.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class BookedProductsDto {

    Double deliveryWeight;

    Double deliveryVolume;

    Boolean fragile;

}
