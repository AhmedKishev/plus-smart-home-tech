package ru.yandex.practicum.telemetry.collector.builders.sensor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.telemetry.collector.model.enums.SensorEventType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SensorEventBuilderConfig {
    ClimateSensorBuilder climateSensorBuilder;
    LightSensorBuilder lightSensorBuilder;
    MotionSensorBuilder motionSensorBuilder;
    SwitchSensorBuilder switchSensorBuilder;
    TemperatureSensorBuilder temperatureSensorBuilder;

    @Bean
    public Map<SensorEventType, SensorEventBuilder> getSensorEventBuilders() {
        Map<SensorEventType, SensorEventBuilder> sensorEventBuilders = new HashMap<>();

        sensorEventBuilders.put(SensorEventType.SWITCH_SENSOR_EVENT, switchSensorBuilder);
        sensorEventBuilders.put(SensorEventType.CLIMATE_SENSOR_EVENT, climateSensorBuilder);
        sensorEventBuilders.put(SensorEventType.LIGHT_SENSOR_EVENT, lightSensorBuilder);
        sensorEventBuilders.put(SensorEventType.MOTION_SENSOR_EVENT, motionSensorBuilder);
        sensorEventBuilders.put(SensorEventType.TEMPERATURE_SENSOR_EVENT, temperatureSensorBuilder);

        return sensorEventBuilders;
    }
}