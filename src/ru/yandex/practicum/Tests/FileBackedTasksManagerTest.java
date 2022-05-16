package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.manager.FileBackedTasksManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    static File file = new File("./Files" , "FileBackedTasksTest.csv");
    private static FileBackedTasksManager fileBackedTasksManager;

    static {
        try {
            fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    public FileBackedTasksManagerTest() {
        super(fileBackedTasksManager);
    }


    @Test
    void shouldBeEmptyTasks() throws ManagerSaveException {
        clearTask();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertTrue(fileBackedTasksManager1.getTaskList().isEmpty());
    }

    @Test
    void shouldBeEpicWithoutSubtask() throws ManagerSaveException {
        clearEpic();
        addEpic();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertFalse(fileBackedTasksManager1.getEpicList().isEmpty());
        assertTrue(fileBackedTasksManager1.getEpicSubtasks(fileBackedTasksManager1.getEpicList().get(0).getId()).isEmpty());
    }

    @Test
    void shouldBeEmptyHistory() throws ManagerSaveException {
        fileBackedTasksManager.clearHistory();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);
        assertTrue(fileBackedTasksManager1.history().isEmpty());
    }

    @Test
    @Override
    public void addEpic() throws ManagerSaveException {
        super.addEpic();
        fileBackedTasksManager.save();
    }

    @Override
    @Test
    public void clearEpic() throws ManagerSaveException {
        super.clearEpic();
        fileBackedTasksManager.save();
    }

    @Override
    @Test
    public void clearTask() throws ManagerSaveException {
        super.clearTask();
        fileBackedTasksManager.save();
    }

}