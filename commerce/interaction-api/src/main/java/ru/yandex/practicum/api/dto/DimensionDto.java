package ru.yandex.practicum.api.dto;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class DimensionDto {

    @Min(1)
    Double width;

    @Min(1)
    Double height;

    @Min(1)
    Double depth;
}
