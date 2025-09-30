package ru.yandex.practicum.shoppingcart.service;

import jakarta.validation.Valid;
import ru.yandex.practicum.shoppingcart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {
    ShoppingCartDto addProduct(String username, Map<UUID, Long> productsIdWithCount);

    void deactivate(String username);

    ShoppingCartDto getCartByUsername(String username);

    ShoppingCartDto deleteByIds(String username, Map<UUID, Long> productIds);

    ShoppingCartDto change(String username, @Valid ChangeProductQuantityRequest changeProductQuantityRequest);
}
