package ru.yandex.practicum.warehouse.mapper;

import ru.yandex.practicum.api.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.model.Booking;

public class BookingMapper {

    public static BookedProductsDto toBookedProductsDto(Booking booking) {
        return BookedProductsDto.builder()
                .fragile(booking.isFragile())
                .deliveryVolume(booking.getDeliveryVolume())
                .deliveryWeight(booking.getDeliveryWeight())
                .build();
    }

}
