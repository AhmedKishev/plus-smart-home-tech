package ru.yandex.practicum.telemetry.collector.service.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;

public abstract class BaseSensorHandler implements SensorEventHandler {
    KafkaEventProducer kafkaEventProducer;
    String topic;

    public BaseSensorHandler(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
        topic = kafkaEventProducer.getKafkaConfig().getTopics().get("sensors-events");
    }

    @Override
    public void handle(SensorEvent sensor) {
        ProducerRecord<String, SpecificRecordBase> record
                = new ProducerRecord<>(topic, null, System.currentTimeMillis(), sensor.getHubId(), toAvro(sensor));
        kafkaEventProducer.sendRecord(record);
    }

    abstract SpecificRecordBase toAvro(SensorEvent sensor);

}
