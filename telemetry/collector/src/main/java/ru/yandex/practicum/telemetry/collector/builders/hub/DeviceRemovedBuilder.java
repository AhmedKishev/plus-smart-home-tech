package ru.yandex.practicum.telemetry.collector.builders.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.producer.KafkaProducer;

@Component
public class DeviceRemovedBuilder extends BaseHubBuilder {
    public DeviceRemovedBuilder(KafkaProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toAvro(HubEvent hubEvent) {
        DeviceRemovedEvent event = (DeviceRemovedEvent) hubEvent;

        return HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(hubEvent.getTimestamp())
                .setPayload(new DeviceRemovedEventAvro(event.getId()))
                .build();
    }
}
