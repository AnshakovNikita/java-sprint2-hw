package ru.yandex.practicum.manager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault(String url) throws InterruptedException {
        return new HTTPTaskManager(url);
    }

    public static TaskManager getDefaultFileBacked(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
