package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    String toString(Task task);

    String toString(Epic epic);

    Task getTask(long id) throws ManagerSaveException;

    Epic getEpic(long id) throws ManagerSaveException;

    Subtask getSubtask(long id) throws ManagerSaveException;

    void addTask(Task task) throws ManagerSaveException;

    void addEpic(Epic epic) throws ManagerSaveException;

    void addSubtask(Subtask subtask, long epicId) throws ManagerSaveException;

    void clearTask() throws ManagerSaveException;

    void clearEpic() throws ManagerSaveException;

    void clearSubtask() throws ManagerSaveException;

    void removeTask(long id) throws ManagerSaveException;

    void removeEpic(long id) throws ManagerSaveException;

    void removeSubtask(long id) throws ManagerSaveException;

    ArrayList<Subtask> getEpicSubtasks(long id);

    ArrayList<Task> getTaskList();

    ArrayList<Epic> getEpicList();

    ArrayList<Subtask> getSubtaskList();

    void updateTask(Task task) throws ManagerSaveException;

    void updateEpic(Epic epic) throws ManagerSaveException;

    void updateSubtask(Subtask subtask) throws ManagerSaveException;

    List<Long> history() throws ManagerSaveException;
}
