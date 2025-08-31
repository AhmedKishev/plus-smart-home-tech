package ru.yandex.practicum.telemetry.collector.builders.hub;

import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;

public interface HubEventBuilder {

    void builder(HubEvent hubEvent);
}
