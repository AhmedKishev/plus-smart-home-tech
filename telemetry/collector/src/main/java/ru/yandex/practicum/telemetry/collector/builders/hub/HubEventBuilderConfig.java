package ru.yandex.practicum.telemetry.collector.builders.hub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.telemetry.collector.model.enums.HubEventType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HubEventBuilderConfig {
    DeviceAddedBuilder deviceAddedBuilder;
    DeviceRemovedBuilder deviceRemovedBuilder;
    ScenarioAddedBuilder scenarioAddedBuilder;
    ScenarioRemovedBuilder scenarioRemovedBuilder;

    @Bean
    public Map<HubEventType, HubEventBuilder> getHubEventBuilders() {
        Map<HubEventType, HubEventBuilder> hubEventBuilders = new HashMap<>();

        hubEventBuilders.put(HubEventType.DEVICE_ADDED, deviceAddedBuilder);
        hubEventBuilders.put(HubEventType.DEVICE_REMOVED, deviceRemovedBuilder);
        hubEventBuilders.put(HubEventType.SCENARIO_ADDED, scenarioAddedBuilder);
        hubEventBuilders.put(HubEventType.SCENARIO_REMOVED, scenarioRemovedBuilder);

        return hubEventBuilders;
    }
}