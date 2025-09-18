package ru.yandex.practicum.analyzer.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final Environment environment;

    @Bean
    Consumer<String, HubEventAvro> getHubEventConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, environment.getProperty("spring.kafka.consumer.client-id"));
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.consumer.key-deserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.consumer.value-deserializer"));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, environment.getProperty("spring.kafka.consumer.enable-auto-commit"));

        return new KafkaConsumer<>(properties);
    }


    @Bean
    Consumer<String, SensorsSnapshotAvro> getSnapshotConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.snapshot-group-id"));
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, environment.getProperty("spring.kafka.consumer.snapshot-client-id"));
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.consumer.key-deserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.consumer.snapshot-value-deserializer"));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, environment.getProperty("spring.kafka.consumer.enable-auto-commit"));

        return new KafkaConsumer<>(properties);
    }
}
