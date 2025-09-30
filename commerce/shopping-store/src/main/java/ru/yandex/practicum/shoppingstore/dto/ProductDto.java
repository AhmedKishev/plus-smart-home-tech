package ru.yandex.practicum.shoppingstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shoppingstore.enums.ProductCategory;
import ru.yandex.practicum.shoppingstore.enums.ProductState;
import ru.yandex.practicum.shoppingstore.enums.QuantityState;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    UUID productId;
    @NotBlank
    private String productName;
    @NotBlank
    String description;
    String imageSrc;
    @NotNull
    QuantityState quantityState;
    @NotNull
    ProductState productState;
    ProductCategory productCategory;
    @NotNull
    Double price;

}
