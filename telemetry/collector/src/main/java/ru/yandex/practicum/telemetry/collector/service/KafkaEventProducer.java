package ru.yandex.practicum.telemetry.collector.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig;

import java.util.concurrent.TimeoutException;

@Component
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class KafkaEventProducer {

    KafkaConfig kafkaConfig;

    KafkaProducer<String, SpecificRecordBase> kafkaProducer;

    public KafkaEventProducer(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
        this.kafkaProducer = new KafkaProducer<>(kafkaConfig.getProducerProperties());
    }

    public void sendRecord(ProducerRecord<String, SpecificRecordBase> record) {
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                // ВОТ ЗДЕСЬ ПРОБЛЕМА!
                if (exception instanceof TimeoutException) {
                    log.error("Таймаут соединения с Kafka");
                } else if (exception instanceof SerializationException) {
                    log.error("Ошибка сериализации данных");
                } else {
                    log.error("Другая ошибка: {}", exception.getMessage());
                }
            }
        });
    }
}