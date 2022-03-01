package ru.yandex.practicum.tracker;

public class Subtask extends Task {

    public int parentId;

    public Subtask(String name, String description) {
        super(name, description);
    }
}
