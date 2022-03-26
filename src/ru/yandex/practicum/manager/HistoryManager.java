package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.ArrayList;


public interface HistoryManager {

    void remove(long id);

    ArrayList<Task> getHistory();

    void add(Task task);
}
