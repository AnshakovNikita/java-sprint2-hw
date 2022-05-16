package ru.yandex.practicum.manager;

import ru.yandex.practicum.Enum.Status;

import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public interface TaskManager {

    Task getTask(long id);

    Epic getEpic(long id);

    Subtask getSubtask(long id);

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask, long epicId);

    void clearTask();

    void clearEpic();

    void clearSubtask();

    void removeTask(long id);

    void removeEpic(long id);

    void removeSubtask(long id);

    List<Subtask> getEpicSubtasks(long id);

    List<Task> getTaskList();

    List<Epic> getEpicList();

    List<Subtask> getSubtaskList();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Long> history();

    Status getEpicStatus(long id);

    void clearHistory();

    long getEpicDuration(long id);

    Optional<LocalDateTime> getEpicStartTime(long id);

    TreeSet<Task> getPrioritizedTasks();

    boolean hasIntersections();
}
