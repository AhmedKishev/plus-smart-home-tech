package ru.yandex.practicum.shoppingstore.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.api.dto.ProductDto;
import ru.yandex.practicum.api.enums.ProductCategory;
import ru.yandex.practicum.api.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.shoppingstore.service.ShoppingService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Validated
public class ShoppingController {

    ShoppingService shoppingService;


    @PutMapping
    ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto) {
        return shoppingService.createProduct(productDto);
    }

    @GetMapping("/{productId}")
    ProductDto getProductById(@PathVariable UUID productId) {
        return shoppingService.getProductById(productId);
    }

    @PostMapping
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        return shoppingService.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    Boolean deleteProductById(@RequestBody UUID productId) {
        return shoppingService.removeProductById(productId);
    }

    @PostMapping("/quantityState")
    ProductDto updateStateProduct(@Valid SetProductQuantityStateRequest quantityState) {
        return shoppingService.updateStateProduct(quantityState);
    }

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam @NotNull ProductCategory category,
                                        Pageable pageableDto) {
        return shoppingService.getProducts(category, pageableDto);
    }
}