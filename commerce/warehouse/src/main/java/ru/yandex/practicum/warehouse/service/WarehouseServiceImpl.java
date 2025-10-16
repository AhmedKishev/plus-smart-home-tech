package ru.yandex.practicum.warehouse.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.api.client.ShoppingStoreClient;
import ru.yandex.practicum.api.dto.AddressDto;
import ru.yandex.practicum.api.dto.BookedProductsDto;
import ru.yandex.practicum.api.dto.ShoppingCartDto;
import ru.yandex.practicum.api.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.api.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.api.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.api.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.warehouse.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductNotFoundInWarehouseException;
import ru.yandex.practicum.warehouse.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.warehouse.mapper.BookingMapper;
import ru.yandex.practicum.warehouse.mapper.WarehouseMapper;
import ru.yandex.practicum.warehouse.model.Address;
import ru.yandex.practicum.warehouse.model.Booking;
import ru.yandex.practicum.warehouse.model.Warehouse;
import ru.yandex.practicum.warehouse.repository.BookingRepository;
import ru.yandex.practicum.warehouse.repository.WarehouseRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    WarehouseRepository warehouseRepository;
    ShoppingStoreClient shoppingStoreClient;
    BookingRepository bookingRepository;

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
                () -> new NoSpecifiedProductInWarehouseException(String.format("Информация о товаре c id: %s  на складе не найдена.", requestDto.getProductId()))
        );
        if (warehouse.getQuantity() == null) {
            warehouse.setQuantity(requestDto.getQuantity());
        } else
            warehouse.setQuantity(warehouse.getQuantity() + requestDto.getQuantity());
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

    @Override
    public void shippedOrder(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        Booking booking = bookingRepository.findByOrderId(shippedToDeliveryRequest.getOrderId());
        booking.setDeliveryId(shippedToDeliveryRequest.getDeliveryId());
    }

    @Override
    public void returnOrder(Map<UUID, Long> products) {
        List<Warehouse> warehouses = warehouseRepository.findAll();

        warehouses.stream()
                .filter(warehouse -> products.containsKey(warehouse.getProductId()))
                .forEach(warehouse -> {
                    UUID productId = warehouse.getProductId();
                    Long quantityToAdd = products.get(productId);
                    warehouse.setQuantity(warehouse.getQuantity() + quantityToAdd);
                    warehouseRepository.save(warehouse);
                });
    }

    @Override
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) {
        Booking booking = bookingRepository.findByOrderId(assemblyProductsForOrderRequest.getOrderId());

        Map<UUID, Long> productsInBooking = booking.getProducts();
        List<Warehouse> productsInWarehouse = warehouseRepository.findAllById(productsInBooking.keySet());
        productsInWarehouse.forEach(warehouse -> {
            if (warehouse.getQuantity() < productsInBooking.get(warehouse.getProductId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouseException("Ошибка, товар из корзины не находится в требуемом количестве на складе.");
            }
        });
        for (Warehouse warehouse : productsInWarehouse) {
            warehouse.setQuantity(warehouse.getQuantity() - productsInBooking.get(warehouse.getProductId()));
        }
        booking.setOrderId(assemblyProductsForOrderRequest.getOrderId());
        return BookingMapper.toBookedProductsDto(booking);

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

}
