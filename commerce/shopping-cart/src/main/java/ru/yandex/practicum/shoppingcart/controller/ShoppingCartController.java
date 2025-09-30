package ru.yandex.practicum.shoppingcart.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingcart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.dto.ShoppingCartDto;
import ru.yandex.practicum.shoppingcart.service.ShoppingCartService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Validated
public class ShoppingCartController {
    ShoppingCartService service;

    @PutMapping
    ShoppingCartDto addProductInCart(@RequestParam String username,
                                     @RequestBody Map<UUID, Long> productsIdWithCount) {
        return service.addProduct(username, productsIdWithCount);
    }

    @DeleteMapping
    void deactivateCart(@RequestParam String username) {
        service.deactivate(username);
    }

    @GetMapping
    ShoppingCartDto getCartByUsername(@RequestParam String username) {
        return service.getCartByUsername(username);
    }


    @PostMapping("/remove")
    ShoppingCartDto deleteProductsThrowId(@RequestParam String username,
                                          @RequestBody Map<UUID, Long> productIds) {
        return service.deleteByIds(username, productIds);
    }


    @PostMapping("/change-quantity")
    ShoppingCartDto changeQuantityProduct(@RequestParam String username,
                                          @RequestBody @Valid ChangeProductQuantityRequest changeProductQuantityRequest) {
        return service.change(username, changeProductQuantityRequest);
    }

}
