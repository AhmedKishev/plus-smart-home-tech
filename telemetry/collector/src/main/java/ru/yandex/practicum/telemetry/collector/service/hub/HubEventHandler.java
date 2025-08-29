package ru.yandex.practicum.telemetry.collector.service.hub;

import ru.yandex.practicum.telemetry.collector.model.enums.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;

public interface HubEventHandler {

    HubEventType getMessageType();

    void handle(HubEvent event);

}
