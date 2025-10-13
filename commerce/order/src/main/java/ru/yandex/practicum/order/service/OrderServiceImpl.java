package ru.yandex.practicum.order.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.api.client.DeliveryClient;
import ru.yandex.practicum.api.client.PaymentClient;
import ru.yandex.practicum.api.client.WarehouseClient;
import ru.yandex.practicum.api.dto.BookedProductsDto;
import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.ShoppingCartDto;
import ru.yandex.practicum.api.enums.OrderState;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;
import ru.yandex.practicum.order.mapper.OrderMapper;
import ru.yandex.practicum.order.model.Order;
import ru.yandex.practicum.order.repository.OrderRepository;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    WarehouseClient warehouseClient;
    PaymentClient paymentClient;
    DeliveryClient deliveryClient;

    @Override
    public OrderDto createNewOrder(CreateNewOrderRequest createNewOrderRequest) {
        Order order = Order.builder()
                .shoppingCartId(createNewOrderRequest.getShoppingCartDto().getShoppingCartId())
                .products(createNewOrderRequest.getShoppingCartDto().getProducts())
                .state(OrderState.NEW)
                .build();
        Order newOrder = orderRepository.save(order);
        BookedProductsDto bookedProductsDto = checkProductInWarehouse(createNewOrderRequest.getShoppingCartDto());
        newOrder.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        newOrder.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        newOrder.setFragile(bookedProductsDto.getFragile());
        newOrder.setProductPrice(getPriceOfProducts(OrderDto.builder().products(createNewOrderRequest.getShoppingCartDto().getProducts()).build()));

        DeliveryDto deliveryDto = DeliveryDto.builder()
                .orderId(newOrder.getOrderId())
                .fromAddress(warehouseClient.getAddress())
                .toAddress(createNewOrderRequest.getAddressDto())
                .build();

        newOrder.setDeliveryId(deliveryClient.createDelivery(deliveryDto).getDeliveryId());

        OrderDto forPaymentAndReturn = OrderMapper.toOrderDto(newOrder);

        paymentClient.createPayment(forPaymentAndReturn);

        return forPaymentAndReturn;
    }


    private BookedProductsDto checkProductInWarehouse(ShoppingCartDto shoppingCartDto) {
        return warehouseClient.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
    }

    private Double getPriceOfProducts(OrderDto orderDto) {
        return paymentClient.productCost(orderDto);
    }

}

