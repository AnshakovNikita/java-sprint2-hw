import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;
import ru.yandex.practicum.tracker.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Manager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int globalId = 1;


        Task getTask(int id) {
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
        }

        Epic getEpic(int id) {
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

        Subtask getSubtask(int id) {
            Subtask subtaskCopy = new Subtask("", "");
            Subtask subtask = null;
            if (subtasks.containsKey(id)) {
                subtask = subtasks.get(id);
                subtaskCopy.setId(subtask.getId());
                subtaskCopy.name = subtask.name;
                subtaskCopy.description = subtask.description;
                subtaskCopy.setStatus(subtask.getStatus());
                subtaskCopy.parentId = subtask.parentId;
            } else {
                System.out.println("такого нет");
            }
            return subtaskCopy;
        }

        void addTask(Task task) {
            task.setId(globalId);
            tasks.put(globalId, task);
            globalId++;
        }

        void addEpic(Epic epic) {
            epic.setId(globalId);
            epics.put(globalId, epic);
            globalId++;
        }

        void addSubtask(Subtask subtask, int epicId) {
            if (epics.containsKey(epicId)) {
                epics.get(epicId);
                subtask.setId(globalId);
                subtask.parentId = epicId;
                subtasks.put(globalId, subtask);
                globalId++;
                Epic epic = epics.get(subtask.parentId);
                updateEpic(epic);
            } else {
                System.out.println("Для создания подзадачи нужен эпик.");
            }
        }

        void clearTask() {
            tasks.clear();
        }

        void clearEpic() {
            epics.clear();
            subtasks.clear();
        }

        void clearSubtask() {
            subtasks.clear();
        }

        void removeTask(int id) {
            if (tasks.containsKey(id)) {
                tasks.remove(id);
                System.out.println("Задача удалена.");
            } else {
                System.out.println("Такой задачи нет.");
            }
        }

        void removeEpic(int id) {
            if (epics.containsKey(id)) {
                HashSet<Integer> set = new HashSet<>();
                for (Subtask sub : subtasks.values()){
                        if (id == sub.parentId) {
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


        void removeSubtask(int id) {
            if (subtasks.containsKey(id)) {
                subtasks.remove(id);
                System.out.println("Подзадача удалена.");
            } else {
                System.out.println("Такой подзадачи нет.");
            }
        }

        ArrayList<Subtask> getEpicSubtasks (int id) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
                if (epics.containsKey(id)) {
                    for (Subtask sub : subtasks.values()) {
                        if (id == sub.parentId) {
                            subtasksList.add(sub);
                        }
                    }
                } else {
                    System.out.println("такого нет");
                }
            return subtasksList;
        }

        ArrayList<Task> getTaskList() {
            ArrayList<Task> taskList = new ArrayList<>();
                for (Task tasks : tasks.values()) {
                    taskList.add(tasks);
                }
            return taskList;
        }

        ArrayList<Epic> getEpicList() {
            ArrayList<Epic> epicList = new ArrayList<>();
                for (Epic epics : epics.values()) {
                    epicList.add(epics);
                }
            return epicList;
        }

        ArrayList<Subtask> getSubtaskList() {
            ArrayList<Subtask> subtaskList = new ArrayList<>();
                for (Subtask subtasks : subtasks.values()) {
                    subtaskList.add(subtasks);
                }
            return subtaskList;
        }

        void updateTask(Task task){
            if(tasks.containsKey(task.getId())) {
                tasks.put(task.getId(), task);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        void updateEpic(Epic epic){
            if(epics.containsKey(epic.getId())) {
                epic.setStatus(getEpicStatus(epic.getId()));
                epics.put(epic.getId(), epic);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        void updateSubtask(Subtask subtask){
            if(subtasks.containsKey(subtask.getId())) {
                subtasks.put(subtask.getId(), subtask);
                Epic epic = epics.get(subtask.parentId);
                updateEpic(epic);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        Status getEpicStatus(int id) {
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
