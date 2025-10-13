package ru.yandex.practicum.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.api.client.ShoppingStoreClient;
import ru.yandex.practicum.api.dto.OrderDto;
import ru.yandex.practicum.api.dto.PaymentDto;
import ru.yandex.practicum.api.enums.PaymentState;
import ru.yandex.practicum.payment.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.payment.mapper.PaymentMapper;
import ru.yandex.practicum.payment.model.Payment;
import ru.yandex.practicum.payment.repository.PaymentRepository;

import java.util.Map;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;
    ShoppingStoreClient shoppingStoreClient;

    @Override
    public Double checkProductCost(OrderDto orderDto) {
        Map<UUID, Long> products = orderDto.getProducts();
        Double totalProductPrice = 0.0;
        if (products == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Не хватает данных для расчет стоимости товаров");
        }
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            UUID productId = entry.getKey();
            Long quantity = entry.getValue();
            totalProductPrice += shoppingStoreClient.getProduct(productId).getPrice() * quantity;
        }
        return totalProductPrice;
    }

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        checkOrder(orderDto);
        Payment payment = Payment.builder()
                .orderId(orderDto.getOrderId())
                .totalPayment(orderDto.getTotalPrice())
                .deliveryTotal(orderDto.getDeliveryPrice())
                .productsTotal(orderDto.getProductPrice())
                .feeTotal(getTax(orderDto.getTotalPrice()))
                .status(PaymentState.PENDING)
                .build();
        return PaymentMapper.toPaymentDto(paymentRepository.save(payment));
    }


    private void checkOrder(OrderDto orderDto) {
        if (orderDto.getDeliveryPrice() == null || orderDto.getProductPrice() == null || orderDto.getTotalPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Не хватает данных для формирования оплаты");
        }
    }

    private Double getTax(Double price) {
        return price * 0.1;
    }

}
