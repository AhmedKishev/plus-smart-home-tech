package ru.yandex.practicum.telemetry.collector.builders.hub;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.producer.KafkaProducer;

@RequiredArgsConstructor
public abstract class BaseHubBuilder implements HubEventBuilder {

    private final KafkaProducer producer;

    @Value("${topic.telemetry-hubs}")
    private String topic;

    @Override
    public void builder(HubEvent event) {
        producer.send(toAvro(event), event.getHubId(), event.getTimestamp(), topic);
    }

    public abstract SpecificRecordBase toAvro(HubEvent hubEvent);
}
