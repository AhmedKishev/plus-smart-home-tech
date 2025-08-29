package ru.yandex.practicum.telemetry.collector.builders.sensor;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.producer.KafkaProducer;

@RequiredArgsConstructor
public abstract class BaseSensorBuilder implements SensorEventBuilder {

    private final KafkaProducer producer;

    @Value("${topic.telemetry-sensors}")
    private String topic;

    @Override
    public void builder(SensorEvent event) {
        producer.send(toAvro(event), event.getHubId(), event.getTimestamp(), topic);
    }

    public abstract SpecificRecordBase toAvro(SensorEvent sensorEvent);
}
