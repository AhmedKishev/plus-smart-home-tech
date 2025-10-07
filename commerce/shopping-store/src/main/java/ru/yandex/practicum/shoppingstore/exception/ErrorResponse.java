package ru.yandex.practicum.shoppingstore.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@AllArgsConstructor
public class ErrorResponse {
    String error;
    RuntimeException exception;
}
