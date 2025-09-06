package ru.yandex.practicum.telemetry.collector.gRPC.builders.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.gRPC.producer.KafkaEventProducer;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorBuilder implements SensorEventBuilder {

    private final KafkaEventProducer producer;

    @Value("${topic.telemetry-sensors}")

    private String topic;

    @Override
    public void builder(SensorEventProto event) {
        producer.send(toAvro(event), event.getHubId(), mapTimestampToInstant(event), topic);
    }

    public Instant mapTimestampToInstant(SensorEventProto event) {
        return Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos());
    }

    public abstract SensorEventAvro toAvro(SensorEventProto sensorEvent);

    public abstract SensorEventProto.PayloadCase getMessageType();
}
