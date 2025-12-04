package ru.yandex.practicum.api.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.api.dto.AddressDto;
import ru.yandex.practicum.api.dto.ShoppingCartDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CreateNewOrderRequest {

    ShoppingCartDto shoppingCartDto;

    AddressDto addressDto;

}
