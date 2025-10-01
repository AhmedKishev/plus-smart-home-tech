package ru.yandex.practicum.shoppingcart.mapper;


import ru.yandex.practicum.intersectionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;

import java.util.Map;
import java.util.UUID;

public class ShoppingCartMapper {

    public static ShoppingCart toShoppingCart(String username, Map<UUID, Long> products) {
        return ShoppingCart.builder()
                .username(username)
                .products(products)
                .active(true)
                .build();
    }

    public static ShoppingCartDto toShoppingCartDto(ShoppingCart save) {
        return ShoppingCartDto.builder()
                .shoppingCartId(save.getShoppingCartId())
                .products(save.getProducts())
                .build();
    }
}
