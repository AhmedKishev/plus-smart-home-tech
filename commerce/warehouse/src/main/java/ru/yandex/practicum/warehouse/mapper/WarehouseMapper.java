package ru.yandex.practicum.warehouse.mapper;

import ru.yandex.practicum.intersectionapi.dto.DimensionDto;
import ru.yandex.practicum.intersectionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.model.Dimension;
import ru.yandex.practicum.warehouse.model.Warehouse;

public class WarehouseMapper {

    public static Warehouse toWarehouse(NewProductInWarehouseRequest requestDto) {
        Dimension dimension = getDimensionByDto(requestDto.getDimension());
        return Warehouse.builder()
                .fragile(requestDto.getFragile())
                .dimension(dimension)
                .productId(requestDto.getProductId())
                .weight(requestDto.getWeight())
                .build();
    }

    private static Dimension getDimensionByDto(DimensionDto dimensionDto) {
        return Dimension.builder()
                .depth(dimensionDto.getDepth())
                .width(dimensionDto.getWidth())
                .height(dimensionDto.getHeight())
                .build();
    }

}
