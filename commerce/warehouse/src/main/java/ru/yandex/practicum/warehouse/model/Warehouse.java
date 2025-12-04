package ru.yandex.practicum.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "warehouse_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Warehouse {
    @Id
    UUID productId;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "fragile")
    Boolean fragile;

    @Embedded
    @Column(name = "dimension")
    Dimension dimension;

    @Column(name = "weight")
    Double weight;

    @Column(name = "deliveryId")
    UUID deliveryId;
}