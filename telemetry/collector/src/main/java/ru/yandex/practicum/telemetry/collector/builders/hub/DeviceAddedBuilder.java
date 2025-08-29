package ru.yandex.practicum.telemetry.collector.builders.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.enums.DeviceType;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.producer.KafkaProducer;

@Component
public class DeviceAddedBuilder extends BaseHubBuilder {
    public DeviceAddedBuilder(KafkaProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toAvro(HubEvent hubEvent) {
        DeviceAddedEvent event = (DeviceAddedEvent) hubEvent;

        return HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(hubEvent.getTimestamp())
                .setPayload(new DeviceAddedEventAvro(event.getId(), mapToDeviceTypeAvro(event.getDeviceType())))
                .build();
    }

    private DeviceTypeAvro mapToDeviceTypeAvro(DeviceType deviceType) {

        switch (deviceType) {
            case LIGHT_SENSOR -> {
                return DeviceTypeAvro.LIGHT_SENSOR;
            }
            case MOTION_SENSOR -> {
                return DeviceTypeAvro.MOTION_SENSOR;
            }
            case SWITCH_SENSOR -> {
                return DeviceTypeAvro.SWITCH_SENSOR;
            }
            case CLIMATE_SENSOR -> {
                return DeviceTypeAvro.CLIMATE_SENSOR;
            }
            case TEMPERATURE_SENSOR -> {
                return DeviceTypeAvro.TEMPERATURE_SENSOR;
            }
            default -> {
                return null;
            }
        }
    }
}
