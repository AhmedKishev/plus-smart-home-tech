package ru.yandex.practicum.warehouse.service;

import jakarta.validation.Valid;
import ru.yandex.practicum.intersectionapi.dto.AddressDto;
import ru.yandex.practicum.intersectionapi.dto.BookedProductsDto;
import ru.yandex.practicum.intersectionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.intersectionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.intersectionapi.request.NewProductInWarehouseRequest;

public interface WarehouseService {
    void newProductInWarehouse(@Valid NewProductInWarehouseRequest requestDto);

    BookedProductsDto checkProductQuantityEnoughForShoppingCart(@Valid ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(@Valid AddProductToWarehouseRequest requestDto);

    AddressDto getAddress();
}
