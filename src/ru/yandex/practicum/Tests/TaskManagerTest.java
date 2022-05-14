package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Enum.Status;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }


    @Test
    void addTask() throws ManagerSaveException {
        assertEquals(0, taskManager.getTaskList().size(), "Список задач не пустой.");
        Task task = new Task("задача1", "описание1");

        assertNotNull(task.getId(), "Id задачи некорректен.");

        taskManager.addTask(task);

        assertTrue(taskManager.getTaskList().contains(task), "Задача не добавилась.");
    }

    @Test
    void addEpic() throws ManagerSaveException {
        assertEquals(0, taskManager.getEpicList().size(), "Список эпиков не пустой.");

        Epic epic = new Epic("эпик1", "описание1");

        assertNotNull(epic.getId(), "Id эпика некорректен.");

        taskManager.addEpic(epic);

        assertTrue(taskManager.getEpicList().contains(epic), "Эпик не добавился.");
    }

    @Test
    void addSubtask() throws ManagerSaveException {
        assertEquals(0, taskManager.getSubtaskList().size(), "Список подзадач не пустой.");

        Epic epic = new Epic("эпик1", "описание1");
        Subtask subtask = new Subtask("подзадача1", "описани1");

        taskManager.addEpic(epic);

        assertNotNull(subtask.getId(), "Id подзадачи некорректен.");

        taskManager.addSubtask(subtask, epic.getId());

        assertNotNull(taskManager.getEpic(subtask.getParentId()));

        assertTrue(taskManager.getSubtaskList().contains(subtask), "Подзадача не добавился.");

        assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
    }

    @Test
    void clearTask() throws ManagerSaveException {
        addTask();
        taskManager.clearTask();

        assertEquals(0, taskManager.getTaskList().size());
    }

    @Test
    void clearEpic() throws ManagerSaveException {
        addEpic();
        taskManager.clearEpic();

        assertEquals(0, taskManager.getEpicList().size());
    }

    @Test
    void clearSubtask() throws ManagerSaveException {
        addSubtask();
        taskManager.clearSubtask();

        assertEquals(0, taskManager.getSubtaskList().size());

        for(Epic epic : taskManager.getEpicList()) {
            assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
        }
    }

    @Test
    void removeTask() throws ManagerSaveException {
        Task task = new Task("задача1", "описание1");
        taskManager.addTask(task);
        taskManager.removeTask(task.getId());

        assertNull(taskManager.getTask(task.getId()));
    }

    @Test
    void removeEpic() throws ManagerSaveException {
        Epic epic = new Epic("epic1", "opisanie1");
        taskManager.addEpic(epic);
        taskManager.removeEpic(epic.getId());

        assertNull(taskManager.getEpic(epic.getId()));
    }

    @Test
    void removeSubtask() throws ManagerSaveException {
        Epic epic = new Epic("эпик1", "описание1");
        Subtask subtask = new Subtask("подзадача1", "описани1");

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask, epic.getId());

        taskManager.removeSubtask(subtask.getId());

        assertNull(taskManager.getSubtask(subtask.getId()));

        assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
    }

    @Test
    void updateTask() throws ManagerSaveException {
        Task task = new Task("задача1", "описание1");
        taskManager.addTask(task);

        Task taskUpdate = taskManager.getTask(task.getId());

        taskUpdate.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(taskUpdate);

        assertEquals(taskUpdate.toString(), taskManager.getTask(task.getId()).toString());

    }

    @Test
    void updateEpic() throws ManagerSaveException {
        Epic epic = new Epic("epic1", "описание1");
        taskManager.addEpic(epic);

        Epic epicUpdate = taskManager.getEpic(epic.getId());

        epicUpdate.name = "newEpic";
        taskManager.updateEpic(epicUpdate);

        assertEquals(epicUpdate.toString(), taskManager.getEpic(epic.getId()).toString());

        assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
    }

    @Test
    void updateSubtask() throws ManagerSaveException {
        Epic epic = new Epic("эпик1", "описание1");
        Subtask subtask = new Subtask("подзадача1", "описани1");

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask, epic.getId());

        Subtask subtaskUpdate = taskManager.getSubtask(subtask.getId());

        subtaskUpdate.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtaskUpdate);

        assertEquals(subtaskUpdate.toString(), taskManager.getSubtask(subtask.getId()).toString());

        assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
    }

    private Status getEpicStatus(ArrayList<Subtask> sub) {
        Status status = Status.NEW;
        for (int i = 0 ; i < sub.size() ; i++) {
            if (i == 0) {
                status = sub.get(i).getStatus();
            }
            if (status != sub.get(i).getStatus()) {
                status = Status.IN_PROGRESS;
                break;
            }
        }
        return status;
    }
}