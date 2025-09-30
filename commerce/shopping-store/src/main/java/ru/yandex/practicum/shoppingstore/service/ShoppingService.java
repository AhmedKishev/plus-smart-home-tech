package ru.yandex.practicum.shoppingstore.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.intersectionapi.dto.ProductDto;
import ru.yandex.practicum.intersectionapi.enums.ProductCategory;
import ru.yandex.practicum.intersectionapi.request.SetProductQuantityStateRequest;

import java.util.UUID;

public interface ShoppingService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(UUID productId);

    ProductDto updateProduct(ProductDto productDto);

    Boolean removeProductById(UUID productId);

    ProductDto updateStateProduct(SetProductQuantityStateRequest quantityState);

    Page<ProductDto> getProducts(ProductCategory productCategory, Pageable pageable);

}
