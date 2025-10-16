package ru.yandex.practicum.order.service;


import jakarta.ws.rs.NotAuthorizedException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.api.client.DeliveryClient;
import ru.yandex.practicum.api.client.PaymentClient;
import ru.yandex.practicum.api.client.ShoppingCartClient;
import ru.yandex.practicum.api.client.WarehouseClient;
import ru.yandex.practicum.api.dto.BookedProductsDto;
import ru.yandex.practicum.api.dto.DeliveryDto;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.ShoppingCartDto;
import ru.yandex.practicum.api.enums.OrderState;
import ru.yandex.practicum.api.request.CreateNewOrderRequest;
import ru.yandex.practicum.api.request.ProductReturnRequest;
import ru.yandex.practicum.order.exception.NoOrderFoundException;
import ru.yandex.practicum.order.mapper.OrderMapper;
import ru.yandex.practicum.order.model.Order;
import ru.yandex.practicum.order.repository.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    WarehouseClient warehouseClient;
    PaymentClient paymentClient;
    DeliveryClient deliveryClient;
    ShoppingCartClient shoppingCartClient;

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

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrderByUsername(String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedException("Не введено имя пользователя");
        }

        ShoppingCartDto shoppingCartDto = shoppingCartClient.getShoppingCart(username);

        List<Order> ordersByUsername = orderRepository.findAllByShoppingCartId(shoppingCartDto.getShoppingCartId());

        return ordersByUsername.stream()
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrder(ProductReturnRequest productReturnRequest) {
        Order findOrderByReturnRequest = getOrderById(productReturnRequest.getOrderId());

        findOrderByReturnRequest.setState(OrderState.PRODUCT_RETURNED);
        warehouseClient.returnOrder(productReturnRequest.getProducts());
        return OrderMapper.toOrderDto(findOrderByReturnRequest);
    }

    @Override
    public OrderDto paymentOrder(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.PAID);
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto paymentFailed(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.PAYMENT_FAILED);
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.DELIVERED);
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto deliveryOrderFailed(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.DELIVERY_FAILED);
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto completedOrder(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.COMPLETED);
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto calculateTotalCostOrder(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setTotalPrice(paymentClient.calculateTotalCostOrder(OrderMapper.toOrderDto(findOrderByOrderId)));
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto calculateDeliveryCost(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setDeliveryPrice(deliveryClient.deliveryCost(OrderMapper.toOrderDto(findOrderByOrderId)));
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.ASSEMBLED);
        warehouseClient.assemblyOrder(new ProductReturnRequest(findOrderByOrderId.getOrderId(), findOrderByOrderId.getProducts()));
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }

    @Override
    public OrderDto assemblyOrderFailed(UUID orderId) {
        Order findOrderByOrderId = getOrderById(orderId);

        findOrderByOrderId.setState(OrderState.ASSEMBLY_FAILED);
        return OrderMapper.toOrderDto(findOrderByOrderId);
    }


    private BookedProductsDto checkProductInWarehouse(ShoppingCartDto shoppingCartDto) {
        return warehouseClient.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
    }

    private Double getPriceOfProducts(OrderDto orderDto) {
        return paymentClient.productCost(orderDto);
    }


    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Заказ не зарегистрирован"));
    }

}

