package ru.yandex.practicum.shoppingstore.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.intersectionapi.dto.ProductDto;
import ru.yandex.practicum.intersectionapi.enums.ProductCategory;
import ru.yandex.practicum.intersectionapi.enums.ProductState;
import ru.yandex.practicum.intersectionapi.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.shoppingstore.exception.NotFoundProduct;
import ru.yandex.practicum.shoppingstore.mapper.ProductMapper;
import ru.yandex.practicum.shoppingstore.model.Product;
import ru.yandex.practicum.shoppingstore.repository.ShoppingRepository;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class ShoppingServiceImpl implements ShoppingService {
    ShoppingRepository shoppingRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product newProduct = ProductMapper.toProduct(productDto);
        return ProductMapper.toProductDto(shoppingRepository.save(newProduct));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(UUID productId) {
        Product findProductById = findProductById(productId);
        return ProductMapper.toProductDto(findProductById);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product findProductById = findProductById(productDto.getProductId());
        update(findProductById, productDto);
        return ProductMapper.toProductDto(shoppingRepository.save(findProductById));
    }

    @Override
    public Boolean removeProductById(UUID productId) {
        Product findProductById = findProductById(productId);
        findProductById.setProductState(ProductState.DEACTIVATE);
        shoppingRepository.save(findProductById);
        return true;
    }

    @Override
    public ProductDto updateStateProduct(SetProductQuantityStateRequest quantityState) {
        Product findProductById = findProductById(quantityState.getProductId());

        findProductById.setQuantityState(quantityState.getQuantityState());
        return ProductMapper.toProductDto(shoppingRepository.save(findProductById));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProducts(ProductCategory productCategory, Pageable pageable) {
        return shoppingRepository.findByProductCategory(productCategory, pageable)
                .map(ProductMapper::toProductDto);
    }


    private void update(Product findProductById, ProductDto productDto) {
        findProductById.setProductCategory(productDto.getProductCategory());
        findProductById.setProductName(productDto.getProductName());
        findProductById.setDescription(productDto.getDescription());
        findProductById.setProductState(productDto.getProductState());
        findProductById.setPrice(productDto.getPrice());
        findProductById.setImageSrc(productDto.getImageSrc());
        findProductById.setQuantityState(productDto.getQuantityState());
    }


    private Product findProductById(UUID productId) {
        return shoppingRepository.findById(productId).orElseThrow(() -> new NotFoundProduct(String.format("Продукта с id: %s нет в наличии", productId)));
    }
}