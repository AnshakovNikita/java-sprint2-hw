package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedList<Task> history = new LinkedList<>();
    Map<Long, Task> historyTaskPosition = new HashMap<>();
    static final byte MAX_HISTORY_LENGTH = 10;

    @Override
    public void add(Task task) {
        if (history.size() > 0 && history.size() > MAX_HISTORY_LENGTH) {
            remove(history.get(0).getId());
        }
        if (historyTaskPosition.containsKey(task.getId())) {
            remove(task.getId());
        }
            history.addLast(task);
            historyTaskPosition.put(task.getId(), task);
    }

    @Override
    public void remove(long id) {
        final Task taskForRemove = historyTaskPosition.get(id);
            history.remove(taskForRemove);
            historyTaskPosition.remove(id);
    }

    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }
}
