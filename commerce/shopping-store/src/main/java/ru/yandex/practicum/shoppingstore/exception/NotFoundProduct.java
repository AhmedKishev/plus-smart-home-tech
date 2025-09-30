package ru.yandex.practicum.shoppingstore.exception;

public class NotFoundProduct extends RuntimeException {
    public NotFoundProduct(String message) {
        super(message);
    }
}
