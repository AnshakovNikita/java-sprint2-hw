package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        @BeforeEach
        public void beforeEach(TestInfo info) throws ManagerSaveException {
            if (info.getDisplayName().equals("shouldBeEmpty()")) {
                return; // skip @BeforeEach in shouldBeEmpty() test
            }
            inMemoryTaskManager.addTask(new Task("Задача1", "описание1"));
            inMemoryTaskManager.addTask(new Task("Задача2", "Описание2"));
            inMemoryTaskManager.addEpic(new Epic("Эпик1", "Описание3"));
            inMemoryTaskManager.addSubtask(new Subtask("Подзадача1эпик1", "Описание4"), 3);
            inMemoryTaskManager.getTask(1);
            inMemoryTaskManager.getTask(2);
            inMemoryTaskManager.getEpic(3);
            inMemoryTaskManager.getSubtask(4);
        }

        @Test
        public void shouldBeEmpty() throws ManagerSaveException {
            inMemoryTaskManager.clearHistory();
            assertTrue(inMemoryTaskManager.history().isEmpty());
        }

        @Test
        public void duplication() throws ManagerSaveException {
            inMemoryTaskManager.getTask(2);
            inMemoryTaskManager.getEpic(3);
            HashMap<Long, Integer> duplication = new HashMap<>();
            for(Long item : inMemoryTaskManager.history()) {
                duplication.put(item, duplication.getOrDefault(item, 0) + 1);
            }

            for(Integer value : duplication.values()) {
                Assertions.assertEquals(1, value);
            }
        }

        @Test
        public void deletionFromHistoryFirst() throws ManagerSaveException {
            Long initialHistory = inMemoryTaskManager.history().get(0);
            inMemoryTaskManager.historyManager.remove(inMemoryTaskManager.history().get(0));
            assertFalse(initialHistory.toString() == inMemoryTaskManager.history().get(0).toString());
        }

        @Test
        public void deletionFromHistoryLast() throws ManagerSaveException {
            Integer lastIndex = inMemoryTaskManager.history().size() - 1;
            Long initialHistory = inMemoryTaskManager.history().get(lastIndex);
            inMemoryTaskManager.historyManager.remove(inMemoryTaskManager.history().get(lastIndex));
            assertFalse(initialHistory.toString() == inMemoryTaskManager.history().get(inMemoryTaskManager.history().size() - 1).toString());
        }

        @Test
        public void deletionFromHistoryMiddle() throws ManagerSaveException {
            Integer middleIndex = Math.round((inMemoryTaskManager.history().size() -1) / 2);
            Long initialHistory = inMemoryTaskManager.history().get(middleIndex);
            inMemoryTaskManager.historyManager.remove(inMemoryTaskManager.history().get(middleIndex));
            assertFalse(initialHistory.toString() == inMemoryTaskManager.history().get(Math.round((inMemoryTaskManager.history().size() -1) / 2)).toString());
        }

}