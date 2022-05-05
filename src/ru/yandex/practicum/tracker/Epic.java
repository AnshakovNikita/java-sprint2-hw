package ru.yandex.practicum.tracker;

import ru.yandex.practicum.manager.TypeTasks;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Long> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return getId() + "," + TypeTasks.EPIC + "," + name + "," + getStatus() + "," + description + ",";
    }

    public static Epic fromString(String value) {
        String[] splittedValue = value.split(",");
        Epic epic = new Epic(splittedValue[2], splittedValue[4]);
        epic.setId(Long.parseLong(splittedValue[0]));
        epic.setStatus(Status.valueOf(splittedValue[3]));
        return epic;
    }


    public void setSubtasks(ArrayList<Long> subtasks) {
        this.subtasks = subtasks;
    }

    public ArrayList<Long> getSubtasks() {
        return subtasks;
    }
}
