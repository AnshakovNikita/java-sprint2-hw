package ru.yandex.practicum.tracker;

import ru.yandex.practicum.Enum.Status;
import ru.yandex.practicum.Enum.TypeTasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Task {
    public String name;
    public String description;
    private long id;
    private Status status;
    private long duration = 0;
    private Optional<LocalDateTime> startTime;
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");



    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    @Override
    public String toString() {
        String startTime = optionalTimeToString(getStartTime());
        String endTime = optionalTimeToString(getEndTime());
        return id + "," + TypeTasks.TASK + "," + name + "," + status + "," + description + "," + duration + ","
                + startTime + "," + endTime + ",";
    }

    public static Task fromString(String value) {
        String[] splittedValue = value.split(",");
        Task task = new Task(splittedValue[2], splittedValue[4]);
        task.id = Long.parseLong(splittedValue[0]);
        task.status = Status.valueOf(splittedValue[3]);
        task.duration = Long.parseLong(splittedValue[5]);
        if(splittedValue.length > 6) {
            task.startTime = Optional.of(LocalDateTime.parse(splittedValue[6], formatter));
        }
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Optional<LocalDateTime> getEndTime() {
        if(startTime != null && startTime.isPresent()) {
            return Optional.of(startTime.get().plusMinutes(duration));
        }
            return Optional.empty();
    }

    public Optional<LocalDateTime> getStartTime() {
        if(startTime == null || !startTime.isPresent()){
            return null;
        }
        return startTime;
    }

    public void setStartTime(Optional<LocalDateTime> startTime) {
        this.startTime = startTime;
    }

    protected String optionalTimeToString(Optional<LocalDateTime> time) {
        String result = "";
        if (time != null && time.isPresent()) {
            result = time.get().format(formatter);
        }
        return result;
    }
}
