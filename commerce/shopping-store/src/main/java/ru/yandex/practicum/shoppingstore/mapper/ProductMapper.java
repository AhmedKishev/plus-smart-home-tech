package ru.yandex.practicum.shoppingstore.mapper;

import ru.yandex.practicum.shoppingstore.dto.ProductDto;
import ru.yandex.practicum.shoppingstore.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toProduct(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.getProductId())
                .productCategory(productDto.getProductCategory())
                .productState(productDto.getProductState())
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .imageSrc(productDto.getImageSrc())
                .quantityState(productDto.getQuantityState())
                .build();
    }

    public static ProductDto toProductDto(Product save) {
        return ProductDto.builder()
                .productId(save.getProductId())
                .productCategory(save.getProductCategory())
                .productState(save.getProductState())
                .productName(save.getProductName())
                .price(save.getPrice())
                .description(save.getDescription())
                .imageSrc(save.getImageSrc())
                .quantityState(save.getQuantityState())
                .build();
    }

    public static List<ProductDto> productsToProductsDto(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toProductDto)
                .collect(Collectors.toList());
    }
}
