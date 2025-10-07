package ru.yandex.practicum.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSpecifiedProductInWarehouseException.class,
            SpecifiedProductAlreadyInWarehouseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse alreadyExistException(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

}
