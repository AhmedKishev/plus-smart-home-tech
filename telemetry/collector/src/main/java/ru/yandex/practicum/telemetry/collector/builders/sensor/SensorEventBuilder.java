package ru.yandex.practicum.telemetry.collector.builders.sensor;

import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

public interface SensorEventBuilder {
    void builder(SensorEvent event);
}
