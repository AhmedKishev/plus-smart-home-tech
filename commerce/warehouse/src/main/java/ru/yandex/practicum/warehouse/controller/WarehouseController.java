package ru.yandex.practicum.warehouse.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.intersectionapi.dto.AddressDto;
import ru.yandex.practicum.intersectionapi.dto.BookedProductsDto;
import ru.yandex.practicum.intersectionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.intersectionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.intersectionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.service.WarehouseService;


@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseController {
    WarehouseService warehouseService;

    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest requestDto) {
        warehouseService.newProductInWarehouse(requestDto);
    }

    @PostMapping("/check")
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        return warehouseService.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
    }

    @PostMapping("/add")
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest requestDto) {
        warehouseService.addProductToWarehouse(requestDto);
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return warehouseService.getAddress();
    }
}
