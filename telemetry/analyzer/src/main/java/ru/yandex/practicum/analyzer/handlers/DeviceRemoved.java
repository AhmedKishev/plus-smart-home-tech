package ru.yandex.practicum.analyzer.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;


@Component
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DeviceRemoved implements HubEventHandler {

    SensorRepository sensorRepository;

    @Override
    public void handle(HubEventAvro hub) {
        DeviceAddedEventAvro deviceAddedEventAvro = (DeviceAddedEventAvro) hub.getPayload();

        sensorRepository.deleteByIdAndHubId(deviceAddedEventAvro.getId(), hub.getHubId());
    }


    @Override
    public String getMessageType() {
        return DeviceRemovedEventAvro.class.getSimpleName();
    }
}
