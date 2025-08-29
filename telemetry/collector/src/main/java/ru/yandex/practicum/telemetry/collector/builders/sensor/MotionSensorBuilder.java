package ru.yandex.practicum.telemetry.collector.builders.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.producer.KafkaProducer;

@Component
public class MotionSensorBuilder extends BaseSensorBuilder {
    public MotionSensorBuilder(KafkaProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toAvro(SensorEvent sensorEvent) {
        MotionSensorEvent event = (MotionSensorEvent) sensorEvent;

        return SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setHubId(sensorEvent.getHubId())
                .setTimestamp(sensorEvent.getTimestamp())
                .setPayload(new MotionSensorAvro(event.getLinkQuality(), event.isMotion(), event.getVoltage()))
                .build();
    }
}
