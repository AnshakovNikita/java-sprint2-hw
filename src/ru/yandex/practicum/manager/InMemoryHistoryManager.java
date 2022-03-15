package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        for (int i = 0; i < history.size(); i++) {
            if (history.size() > 10) {
                history.remove(0);
            }
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
