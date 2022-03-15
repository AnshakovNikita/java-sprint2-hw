package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedList<Task> history = new LinkedList<>();
    static final byte maxHistoryLength = 10;


    @Override
    public void add(Task task) {
        history.add(task);
        for (int i = 0; i < history.size(); i++) {
            if (history.size() > maxHistoryLength) {
                history.removeFirst();
            }
        }

    }

    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }
}
