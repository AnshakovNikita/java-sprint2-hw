package ru.yandex.practicum.tracker;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Long> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }


    public void setSubtasks(ArrayList<Long> subtasks) {
        this.subtasks = subtasks;
    }

    public ArrayList<Long> getSubtasks() {
        return subtasks;
    }
}
