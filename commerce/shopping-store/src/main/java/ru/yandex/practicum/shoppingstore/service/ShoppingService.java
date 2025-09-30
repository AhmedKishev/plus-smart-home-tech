package ru.yandex.practicum.shoppingstore.service;


import org.springframework.data.domain.Page;
import ru.yandex.practicum.shoppingstore.dto.ProductDto;
import ru.yandex.practicum.shoppingstore.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.shoppingstore.enums.ProductCategory;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface ShoppingService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(UUID productId);

    ProductDto updateProduct(ProductDto productDto);

    Boolean removeProductById(UUID productId);

    ProductDto updateStateProduct(SetProductQuantityStateRequest quantityState);

    Page<ProductDto> getProducts(ProductCategory productCategory, Pageable pageable);

}
