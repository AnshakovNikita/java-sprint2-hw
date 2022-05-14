package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.*;
import java.util.stream.Collectors;


public class InMemoryHistoryManager implements HistoryManager {
    LinkedHistoryList<Task> history = new LinkedHistoryList<>();
    Map<Long, LinkedHistoryList.Node> historyTaskPosition = new HashMap<>();
    static final byte MAX_HISTORY_LENGTH = 10;


    static String toString(HistoryManager manager) {
        List<String> historyIds = manager.getHistory().stream().map( item -> String.valueOf(item)).collect(Collectors.toList());

        return String.join(",", historyIds);
    }

    static List<Long> fromString(String value) {
        return Arrays.asList(value.split(","))
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }


    @Override
    public void add(Long taskId) {
        if (history.getSize() > 0 && history.getSize() > MAX_HISTORY_LENGTH) {
            remove(history.getFirst());
        }
        if (historyTaskPosition.containsKey(taskId)) {
            remove(taskId);
        }
        LinkedHistoryList.Node newNode = history.linkLast(taskId);
            historyTaskPosition.put(taskId, newNode);
    }

    @Override
    public void remove(long id) {
        final LinkedHistoryList.Node taskForRemove = historyTaskPosition.get(id);
            history.removeNode(taskForRemove);
            historyTaskPosition.remove(id);
    }

    @Override
    public List<Long> getHistory() {
        return history.getTasks();
    }

    @Override
    public void clearAll() {
        history.clear();
    }

}
