package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.io.*;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager  {

    File file;

    public enum FileReaderMode {
        TASK, HISTORY
    }

    public FileBackedTasksManager(File initialFile) throws IOException {
        file = initialFile;

        Reader fileReader = new FileReader(initialFile);
        BufferedReader br = new BufferedReader(fileReader);
        FileReaderMode mode = FileReaderMode.TASK;

        while (br.ready()) {
            String line = br.readLine();
            if (line.isEmpty()) {
                mode = FileReaderMode.HISTORY;
                continue;
            }
            switch(mode) {
                case TASK:
                    this.parseTask(line);
                    break;
                case HISTORY:
                    this.parseHistory(line);
                    break;
            }
        }

        fileReader.close();
    }

    public static FileBackedTasksManager loadFromFile(File file) throws IOException {
        return new FileBackedTasksManager(file);
    }



    public void save() throws IOException {
        Writer fileWriter = new FileWriter(file);
        fileWriter.write("id,type,name,status,description,epic");
        fileWriter.write("\n");

        for (Task task : tasks.values()) {
            fileWriter.write(task.toString());
            fileWriter.write("\n");
        }
        for (Epic epic : epics.values()) {
            fileWriter.write(epic.toString());
            fileWriter.write("\n");
        }
        for (Subtask subtask : subtasks.values()) {
            fileWriter.write(subtask.toString());
            fileWriter.write("\n");
        }

        fileWriter.write("\n");
        fileWriter.write(InMemoryHistoryManager.toString(historyManager));

        fileWriter.close();
    }


    private void parseTask(String task) throws IOException {
        String[] splittedValue = task.split(",");

        switch(splittedValue[1]){
            case "TASK":
                Task newTask = Task.fromString(task);
                super.addTask(newTask);
                break;
            case "EPIC":
                Epic newEpic = Epic.fromString(task);
                super.addEpic(newEpic);
                break;
            case "SUBTASK":
                Subtask newSubtask = Subtask.fromString(task);
                super.addSubtask(newSubtask, newSubtask.getParentId());
                break;
        }
    }

    private void parseHistory(String history) {
        String[] splittedValue = history.split(",");

        for (String value : splittedValue) {
            historyManager.add(Long.parseLong(value));
        }
    }

    @Override
    public void addTask(Task task) throws IOException {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) throws IOException {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask, long epicId) throws IOException {
        super.addSubtask(subtask, epicId);
        save();
    }

    @Override
    public void clearTask() throws IOException {
        super.clearTask();
        save();
    }

    @Override
    public void clearEpic() throws IOException {
        super.clearEpic();
        save();
    }

    @Override
    public void clearSubtask() throws IOException {
        super.clearSubtask();
        save();
    }

    @Override
    public void removeTask(long id) throws IOException {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(long id) throws IOException {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(long id) throws IOException {
        super.removeSubtask(id);
        save();
    }


    @Override
    public void updateTask(Task task) throws IOException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws IOException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IOException {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public List<Long> history() throws IOException {
        return super.history();
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C://Users//NikitaKub//IdeaProjects//java-sprint2-hw", "FileBackedTasks.csv");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);


        fileBackedTasksManager1.addTask(new Task("Задача1", "описание1"));
        fileBackedTasksManager1.addEpic(new Epic("Эпик1", "Описание3"));
        fileBackedTasksManager1.addSubtask(new Subtask("Подзадача1эпик1", "Описание4"), 2);
        fileBackedTasksManager1.addTask(new Task("Задача2", "описание2"));


        fileBackedTasksManager1.getEpic(2);
        fileBackedTasksManager1.getTask(1);
        fileBackedTasksManager1.getSubtask(3);
        fileBackedTasksManager1.history();


        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(file);


        System.out.println("fileBackedTasksManager1");
        System.out.println(fileBackedTasksManager1.tasks);
        System.out.println(fileBackedTasksManager1.epics);
        System.out.println(fileBackedTasksManager1.subtasks);
        System.out.println(fileBackedTasksManager1.history());

        System.out.println("fileBackedTasksManager2");
        System.out.println(fileBackedTasksManager2.tasks);
        System.out.println(fileBackedTasksManager2.epics);
        System.out.println(fileBackedTasksManager2.subtasks);
        System.out.println(fileBackedTasksManager2.history());
    }
}
