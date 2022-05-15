package ru.yandex.practicum.manager;

import ru.yandex.practicum.Enum.TypeTasks;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;


import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager  {

    File file;

    public enum FileReaderMode {
        TASK, HISTORY
    }

    public FileBackedTasksManager(File initialFile) {
        file = initialFile;
    }

    public static FileBackedTasksManager loadFromFile(File initialFile) throws ManagerSaveException {
        try {
            Reader fileReader = new FileReader(initialFile);
            BufferedReader br = new BufferedReader(fileReader);
            FileReaderMode mode = FileReaderMode.TASK;

            FileBackedTasksManager file = new FileBackedTasksManager(initialFile);
            int pass = -1;

            while (br.ready()) {
                String line = br.readLine();
                pass++;
                if (line.isEmpty()) {
                    mode = FileReaderMode.HISTORY;
                    continue;
                }
                if (pass == 0) {
                    continue;
                }

                switch (mode) {
                    case TASK:
                        file.parseTask(line);
                        break;
                    case HISTORY:
                        file.parseHistory(line);
                        break;
                }
            }

            fileReader.close();

            return file;
        } catch (IOException err) {
            throw new ManagerSaveException(err);
        }
    }



    public void save() throws ManagerSaveException {
        try {
            Writer fileWriter = new FileWriter(file);
            fileWriter.write("id,type,name,status,description,duration,startTime,endTime,epic");
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
        } catch (IOException err) {
            throw new ManagerSaveException(err);
        }
    }


    private void parseTask(String task) {
        String[] splittedValue = task.split(",");

        try {
            TypeTasks type = TypeTasks.valueOf(splittedValue[1]);
            switch(type){
                case TASK:
                    Task newTask = Task.fromString(task);
                    super.addTask(newTask);
                    break;
                case EPIC:
                    Epic newEpic = Epic.fromString(task);
                    super.addEpic(newEpic);
                    break;
                case SUBTASK:
                    Subtask newSubtask = Subtask.fromString(task);
                    super.addSubtask(newSubtask, newSubtask.getParentId());
                    break;
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void parseHistory(String history) {
        List<Long> splittedValue = InMemoryHistoryManager.fromString(history);

        for (Long value : splittedValue) {
            historyManager.add(value);
        }
    }

    @Override
    public void addTask(Task task) throws ManagerSaveException {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) throws ManagerSaveException {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask, long epicId) throws ManagerSaveException {
        super.addSubtask(subtask, epicId);
        save();
    }

    @Override
    public void clearTask() throws ManagerSaveException {
        super.clearTask();
        save();
    }

    @Override
    public void clearEpic() throws ManagerSaveException {
        super.clearEpic();
        save();
    }

    @Override
    public void clearSubtask() throws ManagerSaveException {
        super.clearSubtask();
        save();
    }

    @Override
    public void removeTask(long id) throws ManagerSaveException {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(long id) throws ManagerSaveException {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(long id) throws ManagerSaveException {
        super.removeSubtask(id);
        save();
    }


    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws ManagerSaveException {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public List<Long> history() throws ManagerSaveException {
        return super.history();
    }

    @Override
    public void clearHistory() throws ManagerSaveException {
        super.clearHistory();
        save();
    }

    public static void main(String[] args) throws ManagerSaveException {
        File file = new File("C://Users//NikitaKub//IdeaProjects//java-sprint2-hw", "FileBackedTasks.csv");

        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(file);



        Task task1 = new Task("Задача1", "описание1");
        task1.setDuration(10);
        task1.setStartTime(Optional.of(LocalDateTime.now()));

        Task task2 = new Task("Задача2", "описание1");
        //task2.setDuration(15);
        //task2.setStartTime(Optional.of(LocalDateTime.now()));

        Task task3 = new Task("Задача3", "описание1");
        task3.setDuration(9);
        task3.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(80)));



        fileBackedTasksManager1.addTask(task1);
        fileBackedTasksManager1.addTask(task2);
        fileBackedTasksManager1.addTask(task3);
        fileBackedTasksManager1.addEpic(new Epic("Эпик1", "Описание3"));

        Subtask subtask1 = new Subtask("Подзадача1эпик1", "Описание4");
        subtask1.setDuration(5);
        subtask1.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(300)));

        Subtask subtask2 = new Subtask("Подзадача2эпик1", "Описание4");
        subtask2.setDuration(30);
        subtask2.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(90)));

        fileBackedTasksManager1.addSubtask(subtask1, 4);
        fileBackedTasksManager1.addSubtask(subtask2, 4);


        fileBackedTasksManager1.getEpic(4);
        fileBackedTasksManager1.getTask(1);
        fileBackedTasksManager1.getSubtask(5);
        fileBackedTasksManager1.history();

        System.out.println(fileBackedTasksManager1.getPrioritizedTasks());

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
