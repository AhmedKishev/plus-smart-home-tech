package ru.yandex.practicum.delivery.mapper;

import ru.yandex.practicum.api.dto.AddressDto;
import ru.yandex.practicum.delivery.model.Address;

public class AddressMapper {

    public static Address toAddress(AddressDto addressDto) {
        return Address.builder()
                .house(addressDto.getHouse())
                .city(addressDto.getCity())
                .flat(addressDto.getFlat())
                .country(addressDto.getCountry())
                .street(addressDto.getStreet())
                .build();
    }

    public static AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .house(address.getHouse())
                .city(address.getCity())
                .flat(address.getFlat())
                .country(address.getCountry())
                .street(address.getStreet())
                .build();
    }
}
