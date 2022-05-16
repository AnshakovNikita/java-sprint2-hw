package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Enum.Status;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Task task2 = new Task("задача2", "описание2");

        assertNotNull(task.getId(), "Id задачи некорректен.");

        task.setDuration(10);
        task.setStartTime(Optional.of(LocalDateTime.now()));

        task2.setDuration(10);
        task2.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(5)));

        taskManager.addTask(task);
        taskManager.addTask(task2);


        assertTrue(taskManager.getTaskList().contains(task), "Задача не добавилась.");
        assertFalse(taskManager.getTaskList().contains(task2), "Пересекаемая задача добавилась.");
        taskManager.clearTask();
    }

    @Test
    void addEpic() throws ManagerSaveException {
        assertEquals(0, taskManager.getEpicList().size(), "Список эпиков не пустой.");

        Epic epic = new Epic("эпик1", "описание1");

        assertNotNull(epic.getId(), "Id эпика некорректен.");

        taskManager.addEpic(epic);

        assertTrue(taskManager.getEpicList().contains(epic), "Эпик не добавился.");
        taskManager.clearTask();
    }

    @Test
    void addSubtask() throws ManagerSaveException {
        assertEquals(0, taskManager.getSubtaskList().size(), "Список подзадач не пустой.");

        Epic epic = new Epic("эпик1", "описание1");
        Subtask subtask = new Subtask("подзадача1", "описани1");
        Subtask subtask2 = new Subtask("подзадача2", "описани2");
        Subtask subtask3 = new Subtask("подзадача3", "описани3");

        taskManager.addEpic(epic);

        assertNotNull(subtask.getId(), "Id подзадачи некорректен.");

        subtask.setDuration(10);
        subtask.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(50)));

        subtask2.setDuration(10);
        subtask2.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(51)));

        subtask3.setDuration(10);
        subtask3.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(71)));

        taskManager.addSubtask(subtask, epic.getId());
        taskManager.addSubtask(subtask2, epic.getId());
        taskManager.addSubtask(subtask3, epic.getId());

        assertEquals(20, epic.getDuration(), "Продолжительность эпика не рассчиталась.");

        assertEquals(subtask.getStartTime(), epic.getStartTime(), "Неправильное время начала эпика.");

        assertNotNull(taskManager.getEpic(subtask.getParentId()), "вот тут");

        assertTrue(taskManager.getSubtaskList().contains(subtask), "Подзадача не добавился.");

        assertFalse(taskManager.getSubtaskList().contains(subtask2), "Пересекаемая подзадача добавилась.");

        assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
        taskManager.clearEpic();
        taskManager.clearSubtask();
    }

    @Test
    void clearTask() throws ManagerSaveException {
        taskManager.clearTask();

        assertEquals(0, taskManager.getTaskList().size());
    }

    @Test
    void clearEpic() throws ManagerSaveException {
        taskManager.clearEpic();

        assertEquals(0, taskManager.getEpicList().size());
    }

    @Test
    void clearSubtask() throws ManagerSaveException {
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
        taskManager.clearEpic();
        taskManager.clearSubtask();
    }

    @Test
    void updateTask() throws ManagerSaveException {
        Task task = new Task("задача1", "описание1");
        Task task2 = new Task("задача2", "описание2");


        task2.setDuration(10);
        task2.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(5)));

        taskManager.addTask(task);
        taskManager.addTask(task2);

        Task taskUpdate = taskManager.getTask(task.getId());

        taskUpdate.setStatus(Status.IN_PROGRESS);
        task.setDuration(10);
        task.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(20)));

        taskManager.updateTask(taskUpdate);

        assertEquals(taskUpdate.toString(), taskManager.getTask(task.getId()).toString(), "задача не обновилась.");
        taskManager.clearTask();
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
        taskManager.clearEpic();
    }

    @Test
    void updateSubtask() throws ManagerSaveException {
        Epic epic = new Epic("эпик1", "описание1");
        Subtask subtask = new Subtask("подзадача1", "описани1");
        Subtask subtask2 = new Subtask("подзадача2", "описани2");

        subtask2.setDuration(10);
        subtask2.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(5)));

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask, epic.getId());
        taskManager.addSubtask(subtask2, epic.getId());

        Subtask subtaskUpdate = taskManager.getSubtask(subtask.getId());

        subtaskUpdate.setStatus(Status.IN_PROGRESS);

        subtask.setDuration(10);
        subtask.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(20)));

        taskManager.updateSubtask(subtaskUpdate);

        assertEquals(subtaskUpdate.toString(), taskManager.getSubtask(subtask.getId()).toString());

        assertEquals(taskManager.getEpicStatus(epic.getId()), getEpicStatus(taskManager.getEpicSubtasks(epic.getId())));
        taskManager.clearEpic();
        taskManager.clearTask();
        taskManager.clearSubtask();
    }

    private Status getEpicStatus(List<Subtask> sub) {
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