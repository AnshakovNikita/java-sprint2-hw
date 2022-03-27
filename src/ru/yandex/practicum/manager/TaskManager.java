package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;
import java.util.ArrayList;
import java.util.List;

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

    ArrayList<Subtask> getEpicSubtasks(long id);

    ArrayList<Task> getTaskList();

    ArrayList<Epic> getEpicList();

    ArrayList<Subtask> getSubtaskList();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Task> history();
}
