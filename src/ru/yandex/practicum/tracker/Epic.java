package ru.yandex.practicum.tracker;

import ru.yandex.practicum.Enum.Status;
import ru.yandex.practicum.Enum.TypeTasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Epic extends Task {
    protected List<Long> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        String startTime = optionalTimeToString(getStartTime());
        String endTime = optionalTimeToString(getEndTime());
        return getId() + "," + TypeTasks.EPIC + "," + name + "," + getStatus() + "," + description + "," + getDuration() + ","
                 + startTime + "," + endTime + ",";
    }

    public static Epic fromString(String value) {
        String[] splittedValue = value.split(",");
        Epic epic = new Epic(splittedValue[2], splittedValue[4]);
        epic.setId(Long.parseLong(splittedValue[0]));
        epic.setStatus(Status.valueOf(splittedValue[3]));
        epic.setDuration(Long.parseLong(splittedValue[5]));
        if(splittedValue.length > 6) {
            epic.setStartTime(Optional.of(LocalDateTime.parse(splittedValue[6], formatter)));
        }
        return epic;
    }


    public void setSubtasks(List<Long> subtasks) {
        this.subtasks = subtasks;
    }

    public List<Long> getSubtasks() {
        return subtasks;
    }
}
