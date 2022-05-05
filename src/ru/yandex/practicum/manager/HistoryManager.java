package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.List;


public interface HistoryManager {

    void remove(long id);

    List<Long> getHistory();

    void add(Long taskId);
}
