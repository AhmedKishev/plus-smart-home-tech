package ru.yandex.practicum.analyzer.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
@RequiredArgsConstructor
public class DeviceAdded implements HubEventHandler {

    SensorRepository sensorRepository;

    @Override
    public void handle(HubEventAvro hub) {
        sensorRepository.save(buildToSensor(hub));
    }

    private Sensor buildToSensor(HubEventAvro hub) {
        DeviceAddedEventAvro deviceAddedEventAvro = (DeviceAddedEventAvro) hub.getPayload();

        return Sensor.builder()
                .id(deviceAddedEventAvro.getId())
                .hubId(hub.getHubId())
                .build();
    }

    @Override
    public String getMessageType() {
        return DeviceAddedEventAvro.class.getSimpleName();
    }
}
