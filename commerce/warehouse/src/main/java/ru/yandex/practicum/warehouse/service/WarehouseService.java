package ru.yandex.practicum.warehouse.service;

import jakarta.validation.Valid;
import ru.yandex.practicum.api.dto.AddressDto;
import ru.yandex.practicum.api.dto.BookedProductsDto;
import ru.yandex.practicum.api.dto.ShoppingCartDto;
import ru.yandex.practicum.api.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.api.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.api.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.api.request.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    void newProductInWarehouse(@Valid NewProductInWarehouseRequest requestDto);

    BookedProductsDto checkProductQuantityEnoughForShoppingCart(@Valid ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(@Valid AddProductToWarehouseRequest requestDto);

    AddressDto getAddress();

    void shippedOrder(ShippedToDeliveryRequest shippedToDeliveryRequest);

    void returnOrder(Map<UUID, Long> products);

    BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest);
}
