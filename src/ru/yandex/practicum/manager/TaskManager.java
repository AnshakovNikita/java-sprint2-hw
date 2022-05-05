package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    String toString(Task task);

    String toString(Epic epic);

    Task getTask(long id) throws IOException;

    Epic getEpic(long id) throws IOException;

    Subtask getSubtask(long id) throws IOException;

    void addTask(Task task) throws IOException;

    void addEpic(Epic epic) throws IOException;

    void addSubtask(Subtask subtask, long epicId) throws IOException;

    void clearTask() throws IOException;

    void clearEpic() throws IOException;

    void clearSubtask() throws IOException;

    void removeTask(long id) throws IOException;

    void removeEpic(long id) throws IOException;

    void removeSubtask(long id) throws IOException;

    ArrayList<Subtask> getEpicSubtasks(long id);

    ArrayList<Task> getTaskList();

    ArrayList<Epic> getEpicList();

    ArrayList<Subtask> getSubtaskList();

    void updateTask(Task task) throws IOException;

    void updateEpic(Epic epic) throws IOException;

    void updateSubtask(Subtask subtask) throws IOException;

    List<Long> history() throws IOException;
}
