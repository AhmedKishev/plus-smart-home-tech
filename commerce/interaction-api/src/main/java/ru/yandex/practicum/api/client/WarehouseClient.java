package ru.yandex.practicum.api.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.api.dto.AddressDto;
import ru.yandex.practicum.api.dto.BookedProductsDto;
import ru.yandex.practicum.api.dto.ShoppingCartDto;
import ru.yandex.practicum.api.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.api.request.NewProductInWarehouseRequest;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PutMapping
    void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest requestDto);

    @PostMapping("/check")
    BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto);

    @PostMapping("/add")
    void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest requestDto);

    @GetMapping("/address")
    AddressDto getAddress();

    @PostMapping("/booking")
    BookedProductsDto bookingProducts(@RequestBody @Valid ShoppingCartDto shoppingCartDto);
}