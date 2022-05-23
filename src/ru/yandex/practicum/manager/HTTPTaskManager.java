package ru.yandex.practicum.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.Server.KVTaskClient;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class HTTPTaskManager extends FileBackedTasksManager {
    Gson gson = new Gson();
    public static File file = new File("./Files" , "FileBackedTasks.csv");
    KVTaskClient kvTaskClient;

    public HTTPTaskManager(String url) throws InterruptedException {
        super(file);
        kvTaskClient = new KVTaskClient(url);
    }

    public void loadFromServer() throws InterruptedException {
        System.out.println(kvTaskClient.load("tasks"));
        List<Task> tasksFromJson = gson.fromJson(kvTaskClient.load("tasks"), new TypeToken<List<Task>>(){}.getType());
        if(tasksFromJson != null) {
            for (Task task : tasksFromJson) {
                tasks.put(task.getId(), task);
            }
        }

        List<Epic> epicsFromJson = gson.fromJson(kvTaskClient.load("epics"), new TypeToken<List<Epic>>() {}.getType());
        if(epicsFromJson != null) {
            for (Epic epic : epicsFromJson) {
                epics.put(epic.getId(), epic);
            }
        }

        List<Subtask> subtasksFromJson = gson.fromJson(kvTaskClient.load("subtasks"), new TypeToken<List<Subtask>>() {}.getType());
        if(subtasksFromJson != null) {
            for (Subtask subtask : subtasksFromJson) {
                subtasks.put(subtask.getId(), subtask);
            }
        }
    }

    @Override
    public void addTask(Task task) throws InterruptedException {
        super.addTask(task);
        kvTaskClient.put("tasks", gson.toJson(super.getTaskList()));
    }

    @Override
    public void addEpic(Epic epic) throws InterruptedException {
        super.addEpic(epic);
        kvTaskClient.put("epics", gson.toJson(super.getEpicList()));
    }

    @Override
    public void addSubtask(Subtask subtask, long epicId) throws InterruptedException {
        super.addSubtask(subtask, epicId);
        kvTaskClient.put("subtasks", gson.toJson(super.getSubtaskList()));
    }

    @Override
    public Task getTask(long id) throws InterruptedException {
        System.out.println(kvTaskClient.load("tasks"));
        List<Task> tasks = gson.fromJson(kvTaskClient.load("tasks"), new TypeToken<List<Task>>(){}.getType());
        return tasks.stream().filter(c -> String.valueOf(c.getId()).equals(String.valueOf(id))).findFirst().orElse(null);
    }

    @Override
    public Epic getEpic(long id) throws InterruptedException {
        List<Epic> epics = gson.fromJson(kvTaskClient.load("epics"), new TypeToken<List<Epic>>(){}.getType());
        return epics.stream().filter(c -> String.valueOf(c.getId()).equals(String.valueOf(id))).findFirst().orElse(null);
    }

    @Override
    public Subtask getSubtask(long id) throws InterruptedException {
        List<Subtask> subtasks = gson.fromJson(kvTaskClient.load("subtasks"), new TypeToken<List<Subtask>>(){}.getType());
        return subtasks.stream().filter(c -> String.valueOf(c.getId()).equals(String.valueOf(id))).findFirst().orElse(null);
    }

    public static void main(String[] args) throws InterruptedException {
        HTTPTaskManager httpTaskManager = new HTTPTaskManager("http://localhost:8078");
        httpTaskManager.loadFromServer();

        Task task1 = new Task("Задача1", "описание1");
        task1.setDuration(10);
        task1.setStartTime(Optional.of(LocalDateTime.now()));
        httpTaskManager.addTask(task1);

        Task task3 = new Task("Задача3", "описание1");
        task3.setDuration(9);
        task3.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(80)));
        httpTaskManager.addTask(task3);

        httpTaskManager.getTask(1);

        System.out.println(httpTaskManager.getTaskList());
    }

}
