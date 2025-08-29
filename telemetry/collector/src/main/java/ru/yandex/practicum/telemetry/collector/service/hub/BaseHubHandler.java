package ru.yandex.practicum.telemetry.collector.service.hub;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;


@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseHubHandler implements HubEventHandler {
    KafkaEventProducer kafkaEventProducer;
    String topic;


    public BaseHubHandler(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
        topic = kafkaEventProducer.getKafkaConfig().getTopics().get("hubs-events");
    }

    @Override
    public void handle(HubEvent event) {
        ProducerRecord<String, SpecificRecordBase> record =
                new ProducerRecord<>(
                        topic, null, System.currentTimeMillis(), event.getHubId(), toAvro(event));
        kafkaEventProducer.sendRecord(record);
    }

    abstract SpecificRecordBase toAvro(HubEvent hubEvent);
}
