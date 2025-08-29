package ru.yandex.practicum.telemetry.collector.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import ru.yandex.practicum.telemetry.collector.model.enums.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.enums.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.service.exception.APIError;
import ru.yandex.practicum.telemetry.collector.service.hub.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.service.sensor.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelemetryController {


    Map<HubEventType, HubEventHandler> hubEventHandlerMap;
    Map<SensorEventType, SensorEventHandler> sensorEventHandlerMap;


    public TelemetryController(Set<HubEventHandler> hubEventHandlerSet,
                               Set<SensorEventHandler> sensorEventHandlerSet) {
        this.hubEventHandlerMap = hubEventHandlerSet.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
        this.sensorEventHandlerMap = sensorEventHandlerSet.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    public void addSensorEvent(@RequestBody @Valid SensorEvent sensorEvent) {
        log.info("Call method by path /events/sensors PostMapping {}", sensorEvent);
        if (sensorEventHandlerMap.containsKey(sensorEvent.getType())) {
            sensorEventHandlerMap.get(sensorEvent.getType()).handle(sensorEvent);
        } else {
            throw new APIError("Переданы неверные данные. " + sensorEvent);
        }
    }

    @PostMapping("/hubs")
    public void addHubEvent(@RequestBody @Valid HubEvent hubEvent) {
        log.info("Call method by path /events/sensors PostMapping {}", hubEvent);
        if (hubEventHandlerMap.containsKey(hubEvent.getType())) {
            hubEventHandlerMap.get(hubEvent.getType()).handle(hubEvent);
        } else {
            throw new APIError("Переданы некорректные данные. " + hubEvent);
        }
    }


}
