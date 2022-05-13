package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.yandex.practicum.Enum.Status;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();


    @BeforeEach
    public void beforeEach(TestInfo info) throws ManagerSaveException {
        inMemoryTaskManager.addEpic(new Epic("Эпик1", "Описание1"));
        if (info.getDisplayName().equals("shouldBeEmpty()")) {
            return; // skip @BeforeEach in shouldBeEmpty() test
        }
        inMemoryTaskManager.addSubtask(new Subtask("Подзадача1эпик1", "Описание2"), 1);
        inMemoryTaskManager.addSubtask(new Subtask("Подзадача2эпик1", "Описание3"), 1);

    }

    @Test
    public void shouldBeEmpty() {
        assertTrue(inMemoryTaskManager.getEpicSubtasks(1).isEmpty());
    }

    @Test
    public void allSubtasksWithStatusNew() {
        for(Subtask subtask : inMemoryTaskManager.getEpicSubtasks(1)) {
            Assertions.assertEquals(Status.NEW, subtask.getStatus());
        }
    }

    @Test
    public void allSubtasksWithStatusDone() throws ManagerSaveException {
        for(Subtask subtaskNewStatus : inMemoryTaskManager.getEpicSubtasks(1)) {
            subtaskNewStatus.setStatus(Status.DONE);
            inMemoryTaskManager.updateSubtask(subtaskNewStatus);
        }

        for(Subtask subtask : inMemoryTaskManager.getEpicSubtasks(1)) {
            Assertions.assertEquals(Status.DONE, subtask.getStatus());
        }
    }

    @Test
    public void allSubtasksWithStatusInProgress() {

        for(Subtask subtaskNewStatus : inMemoryTaskManager.getEpicSubtasks(1)) {
            subtaskNewStatus.setStatus(Status.IN_PROGRESS);
        }

        for(Subtask subtask : inMemoryTaskManager.getEpicSubtasks(1)) {
            Assertions.assertEquals(Status.IN_PROGRESS, subtask.getStatus());
        }
    }

    @Test
    public void subtasksWithStatusDoneAndNew() throws ManagerSaveException {
        Subtask subtaskNewStatus = inMemoryTaskManager.getEpicSubtasks(1).get(0);
        subtaskNewStatus.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subtaskNewStatus);

        for(Subtask subtask : inMemoryTaskManager.getEpicSubtasks(1)) {
            assertTrue(List.of(Status.DONE, Status.NEW).contains(subtask.getStatus()));
        }
    }

}