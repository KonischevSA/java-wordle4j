package ru.yandex.practicum.exceptions;

public class WrongCommandException extends Exception {
    public WrongCommandException(String message) {
        super(message);
    }
}
