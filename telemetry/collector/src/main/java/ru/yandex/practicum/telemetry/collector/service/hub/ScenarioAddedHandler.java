package ru.yandex.practicum.telemetry.collector.service.hub;

import ru.yandex.practicum.telemetry.collector.model.enums.ActionDevice;
import ru.yandex.practicum.telemetry.collector.model.enums.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.enums.Operations;
import ru.yandex.practicum.telemetry.collector.model.enums.TypesConditions;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceAction;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioCondition;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;

import java.util.List;

@Component
public class ScenarioAddedHandler extends BaseHubHandler {
    public ScenarioAddedHandler(KafkaEventProducer kafkaEventProducer) {
        super(kafkaEventProducer);
    }

    @Override
    SpecificRecordBase toAvro(HubEvent hubEvent) {
        ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) hubEvent;

        List<DeviceActionAvro> actionAvros = scenarioAddedEvent.getActions().stream()
                .map(this::toDeviceActionAvro).toList();

        List<ScenarioConditionAvro> conditionAvros = scenarioAddedEvent.getConditions().stream()
                .map(this::toScenarioConditionAvro).toList();

        return ScenarioAddedEventAvro.newBuilder()
                .setActions(actionAvros)
                .setConditions(conditionAvros)
                .setName(scenarioAddedEvent.getName())
                .build();
    }

    private ScenarioConditionAvro toScenarioConditionAvro(ScenarioCondition scenarioCondition) {
        return ScenarioConditionAvro.newBuilder()
                .setType(toConditionTypeAvro(scenarioCondition.getType()))
                .setValue(scenarioCondition.getValue())
                .setOperation(toConditionOperationAvro(scenarioCondition.getOperation()))
                .setSensorId(scenarioCondition.getSensorId())
                .build();
    }

    private ConditionTypeAvro toConditionTypeAvro(TypesConditions type) {
        return switch (type) {
            case TypesConditions.CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case TypesConditions.HUMIDITY -> ConditionTypeAvro.HUMIDITY;
            case TypesConditions.LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
            case TypesConditions.MOTION -> ConditionTypeAvro.MOTION;
            case TypesConditions.SWITCH -> ConditionTypeAvro.SWITCH;
            case TypesConditions.TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
        };
    }

    private ConditionOperationAvro toConditionOperationAvro(Operations operation) {
        return switch (operation) {
            case Operations.EQUALS -> ConditionOperationAvro.EQUALS;
            case Operations.GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case Operations.LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
        };
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }


    private DeviceActionAvro toDeviceActionAvro(DeviceAction deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(toActionTypeAvro(deviceAction.getType()))
                .setValue(deviceAction.getValue())
                .build();
    }

    private ActionTypeAvro toActionTypeAvro(ActionDevice actionType) {
        return switch (actionType) {
            case ActionDevice.ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case ActionDevice.DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case ActionDevice.INVERSE -> ActionTypeAvro.INVERSE;
            case ActionDevice.SET_VALUE -> ActionTypeAvro.SET_VALUE;
        };
    }

}
