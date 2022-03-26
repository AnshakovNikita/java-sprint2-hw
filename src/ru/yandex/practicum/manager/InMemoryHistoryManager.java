package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {
    LinkedHistoryList<Task> history = new LinkedHistoryList<>();
    Map<Long, LinkedHistoryList.Node> historyTaskPosition = new HashMap<>();
    static final byte MAX_HISTORY_LENGTH = 10;

    @Override
    public void add(Task task) {
        if (history.getSize() > 0 && history.getSize() > MAX_HISTORY_LENGTH) {
            remove(history.getFirst().getId());
        }
        if (historyTaskPosition.containsKey(task.getId())) {
            remove(task.getId());
        }
        LinkedHistoryList.Node newNode = history.linkLast(task);
            historyTaskPosition.put(task.getId(), newNode);
    }

    @Override
    public void remove(long id) {
        final LinkedHistoryList.Node taskForRemove = historyTaskPosition.get(id);
            history.removeNode(taskForRemove);
            historyTaskPosition.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history.getTasks();
    }



}
