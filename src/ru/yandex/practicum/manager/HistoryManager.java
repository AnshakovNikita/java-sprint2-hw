package ru.yandex.practicum.manager;

import java.util.List;


public interface HistoryManager {

    void remove(long id);

    List<Long> getHistory();

    void add(Long taskId);
}
