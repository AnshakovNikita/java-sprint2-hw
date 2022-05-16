package ru.yandex.practicum.Exception;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(Throwable err) {
        super(err);
    }
}
