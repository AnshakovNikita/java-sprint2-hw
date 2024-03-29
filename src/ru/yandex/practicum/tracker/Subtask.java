package ru.yandex.practicum.tracker;

import ru.yandex.practicum.Enum.Status;
import ru.yandex.practicum.Enum.TypeTasks;

import java.time.LocalDateTime;
import java.util.Optional;

public class Subtask extends Task {

    private long parentId;

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
        String startTime = optionalTimeToString(getStartTime());
        String endTime = optionalTimeToString(getEndTime());
        return getId() + "," + TypeTasks.SUBTASK + "," + name + "," + getStatus() + ","  + description + "," + getDuration() + ","
                + startTime + "," + endTime + "," + parentId;
    }

    public static Subtask fromString(String value) {
        String[] splittedValue = value.split(",");
        Subtask subtask = new Subtask(splittedValue[2], splittedValue[4]);
        subtask.setId(Long.parseLong(splittedValue[0]));
        subtask.setStatus(Status.valueOf(splittedValue[3]));
        subtask.setParentId(Long.parseLong(splittedValue[8]));
        subtask.setDuration(Long.parseLong(splittedValue[5]));
        if(splittedValue.length > 6) {
            subtask.setStartTime(Optional.of(LocalDateTime.parse(splittedValue[6], formatter)));
        }
        return subtask;
    }

}
