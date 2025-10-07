package ru.yandex.practicum.warehouse.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Embeddable
public class Dimension {
    Double width;
    Double height;
    Double depth;
}
