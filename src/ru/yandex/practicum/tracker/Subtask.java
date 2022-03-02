package ru.yandex.practicum.tracker;

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
}
