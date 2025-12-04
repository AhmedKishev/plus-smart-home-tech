package ru.yandex.practicum.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.api.client.DeliveryClient;
import ru.yandex.practicum.api.client.PaymentClient;
import ru.yandex.practicum.api.client.ShoppingCartClient;
import ru.yandex.practicum.api.client.WarehouseClient;

@SpringBootApplication
@EnableFeignClients(clients = {WarehouseClient.class, DeliveryClient.class, PaymentClient.class, ShoppingCartClient.class})
@EnableDiscoveryClient
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}