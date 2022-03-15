package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void add(Task task);
}
