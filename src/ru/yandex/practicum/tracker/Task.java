package ru.yandex.practicum.tracker;

import ru.yandex.practicum.manager.TypeTasks;

public class Task {
    public String name;
    public String description;
    private long id;
    private Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + "," + TypeTasks.TASK + "," + name + "," + status + "," + description + ",";
    }

    public static Task fromString(String value) {
        String[] splittedValue = value.split(",");
        Task task = new Task(splittedValue[2], splittedValue[4]);
        task.id = Long.parseLong(splittedValue[0]);
        task.status = Status.valueOf(splittedValue[3]);
        return task;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }
}
