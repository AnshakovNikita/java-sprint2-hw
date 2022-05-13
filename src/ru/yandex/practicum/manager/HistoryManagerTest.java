package ru.yandex.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        @BeforeEach
        public void beforeEach() throws ManagerSaveException {
            inMemoryTaskManager.addTask(new Task("Задача1", "описание1"));

            inMemoryTaskManager.addTask(new Task("Задача2", "Описание2"));

            inMemoryTaskManager.addEpic(new Epic("Эпик1", "Описание3"));

            inMemoryTaskManager.addSubtask(new Subtask("Подзадача1эпик1", "Описание4"), 3);
        }

}