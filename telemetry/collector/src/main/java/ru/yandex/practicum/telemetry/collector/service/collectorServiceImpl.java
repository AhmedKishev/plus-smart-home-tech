package ru.yandex.practicum.telemetry.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.collector.builders.hub.HubEventBuilder;
import ru.yandex.practicum.telemetry.collector.builders.sensor.SensorEventBuilder;
import ru.yandex.practicum.telemetry.collector.model.enums.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.enums.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class collectorServiceImpl implements CollectorService {

    private final Map<SensorEventType, SensorEventBuilder> sensorEventBuilders;
    private final Map<HubEventType, HubEventBuilder> hubEventBuilders;

    @Override
    public void collectSensorEvent(SensorEvent sensor) {
        sensorEventBuilders.get(sensor.getType()).builder(sensor);
    }

    @Override
    public void collectHubEvent(HubEvent hub) {
        hubEventBuilders.get(hub.getType()).builder(hub);
    }
}
