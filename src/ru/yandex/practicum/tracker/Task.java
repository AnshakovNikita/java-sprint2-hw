package ru.yandex.practicum.tracker;

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



    @Override
    public String toString() {
        return "Id: " + id + " Название: " + name + " Описание: " + description + " Статус: " + status;
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
