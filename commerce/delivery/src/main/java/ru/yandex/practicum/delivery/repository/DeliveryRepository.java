package ru.yandex.practicum.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.delivery.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
