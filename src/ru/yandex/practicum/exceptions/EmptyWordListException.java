package ru.yandex.practicum.exceptions;

public class EmptyWordListException extends RuntimeException {
    public EmptyWordListException(String message) {
        super(message);
    }
}
