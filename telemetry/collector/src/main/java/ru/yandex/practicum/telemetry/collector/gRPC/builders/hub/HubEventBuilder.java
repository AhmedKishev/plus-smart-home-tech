package ru.yandex.practicum.telemetry.collector.gRPC.builders.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

public interface HubEventBuilder {

    void builder(HubEventProto hubEvent);

    HubEventProto.PayloadCase getMessageType();
}
