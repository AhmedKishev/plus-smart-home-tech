package ru.yandex.practicum.telemetry.collector.model.hub;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.telemetry.collector.model.enums.ActionDevice;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class DeviceAction {

    @NotBlank
    String sensorId;

    @NotNull
    ActionDevice type;

    Integer value;
}
