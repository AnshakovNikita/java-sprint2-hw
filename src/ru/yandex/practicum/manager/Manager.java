package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;
import ru.yandex.practicum.tracker.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Manager {

    HashMap<Long, Task> tasks = new HashMap<>();
    HashMap<Long, Epic> epics = new HashMap<>();
    HashMap<Long, Subtask> subtasks = new HashMap<>();
    ArrayList<Subtask> sub = new ArrayList<>();
    long globalId = 1; //Исправил


        public Task getTask(long id) {
            Task taskCopy = new Task("", "");
            Task task = null;
            if (tasks.containsKey(id)) {
                task = tasks.get(id);
                taskCopy.setId(task.getId());
                taskCopy.name = task.name;
                taskCopy.description = task.description;
                taskCopy.setStatus(task.getStatus());
            } else {
                System.out.println("такого нет");
            }
            return taskCopy;
        } //в ТЗ есть пункт "Получение по идентификатору."

        public Epic getEpic(long id) {
            Epic epicCopy = new Epic("", "");
            Epic epic = null;
            if (epics.containsKey(id)) {
                epic = epics.get(id);
                epicCopy.setId(epic.getId());
                epicCopy.name = epic.name;
                epicCopy.description = epic.description;
                epicCopy.setStatus(epic.getStatus());
            } else {
                System.out.println("такого нет");
            }
            return epicCopy;
        }

        public Subtask getSubtask(long id) {
            Subtask subtaskCopy = new Subtask("", "");
            Subtask subtask = null;
            if (subtasks.containsKey(id)) {
                subtask = subtasks.get(id);
                subtaskCopy.setId(subtask.getId());
                subtaskCopy.name = subtask.name;
                subtaskCopy.description = subtask.description;
                subtaskCopy.setStatus(subtask.getStatus());
                subtaskCopy.setParentId(subtask.getParentId());
            } else {
                System.out.println("такого нет");
            }
            return subtaskCopy;
        }

        public void addTask(Task task) {
            task.setId(globalId);
            tasks.put(globalId, task);
            globalId++;
        }

        public void addEpic(Epic epic) {
            epic.setId(globalId);
            epics.put(globalId, epic);
            globalId++;
        }

        public void addSubtask(Subtask subtask, long epicId) {

            if (epics.containsKey(epicId)) {
                epics.get(epicId);
                subtask.setId(globalId);
                subtask.setParentId(epicId);
                subtasks.put(globalId, subtask);
                globalId++;
                Epic epic = epics.get(subtask.getParentId());
                updateEpic(epic);
                sub.add(subtask);
                epic.setSubtasks(sub);
            } else {
                System.out.println("Для создания подзадачи нужен эпик.");
            }
        }

        public void clearTask() {
            tasks.clear();
        }

        public void clearEpic() {
            epics.clear();
            subtasks.clear();
        }

        public void clearSubtask() {
            subtasks.clear();
        }

        public void removeTask(long id) {
            if (tasks.containsKey(id)) {
                tasks.remove(id);
                System.out.println("Задача удалена.");
            } else {
                System.out.println("Такой задачи нет.");
            }
        }

        public void removeEpic(long id) {
            if (epics.containsKey(id)) {
                HashSet<Long> set = new HashSet<>();
                for (Subtask sub : subtasks.values()){
                        if (id == sub.getParentId()) {
                            set.add(sub.getId());
                        }
                }
                subtasks.keySet().removeAll(set);
                epics.remove(id);
                System.out.println("Эпик удален.");
            } else {
                System.out.println("Такого эпика нет.");
            }
        }


        public void removeSubtask(long id) {
            if (subtasks.containsKey(id)) {
                subtasks.remove(id);
                System.out.println("Подзадача удалена.");
            } else {
                System.out.println("Такой подзадачи нет.");
            }
        }

        public ArrayList<Subtask> getEpicSubtasks (long id) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
                if (epics.containsKey(id)) {
                    for (Subtask sub : subtasks.values()) {
                        if (id == sub.getParentId()) {
                            subtasksList.add(sub);
                        }
                    }
                } else {
                    System.out.println("такого нет");
                }
            return subtasksList;
        }

        public ArrayList<Task> getTaskList() {
            ArrayList<Task> taskList = new ArrayList<>();
                for (Task tasks : tasks.values()) {
                    taskList.add(tasks);
                }
            return taskList;
        }

        public ArrayList<Epic> getEpicList() {
            ArrayList<Epic> epicList = new ArrayList<>();
                for (Epic epics : epics.values()) {
                    epicList.add(epics);
                }
            return epicList;
        }

        public ArrayList<Subtask> getSubtaskList() {
            ArrayList<Subtask> subtaskList = new ArrayList<>();
                for (Subtask subtasks : subtasks.values()) {
                    subtaskList.add(subtasks);
                }
            return subtaskList;
        }

        public void updateTask(Task task){
            if(tasks.containsKey(task.getId())) {
                tasks.put(task.getId(), task);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        public void updateEpic(Epic epic){
            if(epics.containsKey(epic.getId())) {
                epic.setStatus(getEpicStatus(epic.getId()));
                epics.put(epic.getId(), epic);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        public void updateSubtask(Subtask subtask){
            if(subtasks.containsKey(subtask.getId())) {
                subtasks.put(subtask.getId(), subtask);
                Epic epic = epics.get(subtask.getParentId());
                updateEpic(epic);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        public Status getEpicStatus(long id) {
           Status status = Status.NEW;
           ArrayList < Subtask > sub = getEpicSubtasks(id);
           for (int i = 0 ; i < sub.size() ; i++) {
               if (i == 0) {
                   status = sub.get(i).getStatus();
               }
               if (status != sub.get(i).getStatus()) {
                   status = Status.IN_PROGRESS;
                   break;
               }
           }
            return status;
        }

}
