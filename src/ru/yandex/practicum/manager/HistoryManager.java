package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.LinkedList;

public interface HistoryManager {

    LinkedList<Task> getHistory();

    void add(Task task);
}
