package ru.yandex.practicum.analyzer.handlers;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.analyzer.repository.ActionRepository;
import ru.yandex.practicum.analyzer.repository.ConditionRepository;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ScenarioAdded implements HubEventHandler {

    ScenarioRepository scenarioRepository;
    SensorRepository sensorRepository;
    ConditionRepository conditionRepository;
    ActionRepository actionRepository;

    @Override
    public void handle(HubEventAvro hub) {
        ScenarioAddedEventAvro scenarioAdded = (ScenarioAddedEventAvro) hub.getPayload();

        Scenario scenario = scenarioRepository.findByHubIdAndName(hub.getHubId(),
                scenarioAdded.getName()).orElseGet(() -> scenarioRepository.save(buildToScenario(hub, scenarioAdded)));

        if (checkSensorsInScenarioConditions(scenarioAdded, hub.getHubId())) {
            conditionRepository.saveAll(buildToCondition(scenarioAdded, scenario));
        }

    }

    private Set<Condition> buildToCondition(ScenarioAddedEventAvro scenarioAdded, Scenario scenario) {
        return scenarioAdded.getConditions().stream()
                .map(c -> Condition.builder()
                        .sensor(sensorRepository.findById(Long.valueOf(c.getSensorId())).orElseThrow())
                        .scenario(scenario)
                        .type(c.getType())
                        .operation(c.getOperation())
                        .value(setValue(c.getValue()))
                        .build())
                .collect(Collectors.toSet());
    }

    private Integer setValue(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return (Boolean) value ? 1 : 0;
        }
    }

    private Boolean checkSensorsInScenarioConditions(ScenarioAddedEventAvro scenarioAdded, String hubId) {
        return sensorRepository.existsByIdInAndHubId(scenarioAdded.getConditions().stream()
                .map(ScenarioConditionAvro::getSensorId)
                .toList(), hubId);
    }

    private Scenario buildToScenario(HubEventAvro hub, ScenarioAddedEventAvro scenarioAdded) {

        return Scenario.builder()
                .name(scenarioAdded.getName())
                .hubId(hub.getHubId())
                .build();
    }

    @Override
    public String getMessageType() {
        return ScenarioAddedEventAvro.class.getSimpleName();
    }
}
