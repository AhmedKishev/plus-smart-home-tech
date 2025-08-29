package ru.yandex.practicum.telemetry.collector.service.exception;

public class APIError extends RuntimeException {
    public APIError(String message) {
        super(message);
    }
}
