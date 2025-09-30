package ru.yandex.practicum.warehouse.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.intersectionapi.dto.AddressDto;
import ru.yandex.practicum.intersectionapi.dto.BookedProductsDto;
import ru.yandex.practicum.intersectionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.intersectionapi.enums.QuantityState;
import ru.yandex.practicum.intersectionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.intersectionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductNotFoundInWarehouseException;
import ru.yandex.practicum.warehouse.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.warehouse.mapper.WarehouseMapper;
import ru.yandex.practicum.warehouse.model.Address;
import ru.yandex.practicum.warehouse.model.Warehouse;
import ru.yandex.practicum.warehouse.repository.WarehouseRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    WarehouseRepository warehouseRepository;
    ru.yandex.practicum.interactionapi.feign.ShoppingStoreClient shoppingStoreClient;

    @Override
    public void newProductInWarehouse(NewProductInWarehouseRequest requestDto) {
        warehouseRepository.findById(requestDto.getProductId()).ifPresent(warehouse -> {
            throw new SpecifiedProductAlreadyInWarehouseException("Ошибка, товар с таким описанием уже зарегистрирован на складе.");
        });
        Warehouse warehouse = WarehouseMapper.toWarehouse(requestDto);
        warehouseRepository.save(warehouse);
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto) {
        Map<UUID, Long> products = shoppingCartDto.getProducts();
        Set<UUID> cartProductIds = products.keySet();
        Map<UUID, Warehouse> warehouseProducts = warehouseRepository.findAllById(cartProductIds)
                .stream()
                .collect(Collectors.toMap(Warehouse::getProductId, Function.identity()));

        Set<UUID> productIds = warehouseProducts.keySet();
        cartProductIds.forEach(id -> {
            if (!productIds.contains(id)) {
                throw new ProductNotFoundInWarehouseException("Ошибка, товар не находится на складе.");
            }
        });
        products.forEach((key, value) -> {
            if (warehouseProducts.get(key).getQuantity() < value) {
                throw new ProductInShoppingCartLowQuantityInWarehouseException("Ошибка, товар из корзины не находится в требуемом количестве на складе");
            }
        });

        return getBookedProducts(warehouseProducts.values(), products);
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest requestDto) {
        Warehouse warehouse = warehouseRepository.findById(requestDto.getProductId()).orElseThrow(
                () -> new NoSpecifiedProductInWarehouseException("Информация о товаре " + requestDto.getProductId() + " на складе не найдена.")
        );
        warehouse.setQuantity(warehouse.getQuantity() + requestDto.getQuantity());
        updateProductQuantityInShoppingStore(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto getAddress() {
        String address = Address.CURRENT_ADDRESS;
        return AddressDto.builder()
                .country(address)
                .city(address)
                .street(address)
                .house(address)
                .flat(address)
                .build();
    }

    private BookedProductsDto getBookedProducts(Collection<Warehouse> productList,
                                                Map<UUID, Long> cartProducts) {
        return BookedProductsDto.builder()
                .fragile(productList.stream().anyMatch(Warehouse::getFragile))
                .deliveryWeight(productList.stream()
                        .mapToDouble(p -> p.getWeight() * cartProducts.get(p.getProductId()))
                        .sum())
                .deliveryVolume(productList.stream()
                        .mapToDouble(p ->
                                p.getDimension().getWidth() * p.getDimension().getHeight() * p.getDimension().getDepth() * cartProducts.get(p.getProductId()))
                        .sum())
                .build();
    }

    private void updateProductQuantityInShoppingStore(Warehouse product) {
        UUID productId = product.getProductId();
        QuantityState quantityState;
        Long quantity = product.getQuantity();

        if (quantity == 0) {
            quantityState = QuantityState.ENDED;
        } else if (quantity < 10) {
            quantityState = QuantityState.ENOUGH;
        } else if (quantity < 100) {
            quantityState = QuantityState.FEW;
        } else {
            quantityState = QuantityState.MANY;
        }
        shoppingStoreClient.setProductQuantityState(productId, quantityState);
    }


}
