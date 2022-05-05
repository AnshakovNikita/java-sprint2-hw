package ru.yandex.practicum.tracker;

import ru.yandex.practicum.manager.TypeTasks;

public class Subtask extends Task {

    private long parentId; //Исправил

    public Subtask(String name, String description) {
        super(name, description);
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return getId() + "," + TypeTasks.SUBTASK + "," + name + "," + getStatus() + "," + description + "," + parentId;
    }

    public static Subtask fromString(String value) {
        String[] splittedValue = value.split(",");
        Subtask subtask = new Subtask(splittedValue[2], splittedValue[4]);
        subtask.setId(Long.parseLong(splittedValue[0]));
        subtask.setStatus(Status.valueOf(splittedValue[3]));
        subtask.setParentId(Long.parseLong(splittedValue[5]));
        return subtask;
    }

}
