package ru.yandex.practicum.manager;

import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;
import ru.yandex.practicum.Enum.Status;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    HashMap<Long, Task> tasks = new HashMap<>();
    HashMap<Long, Epic> epics = new HashMap<>();
    HashMap<Long, Subtask> subtasks = new HashMap<>();
    long globalId = 1;
    public static HistoryManager historyManager = Managers.getDefaultHistory();
    private TreeSet<Task> prioritizedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if(o1.getStartTime() == null && o2.getStartTime() == null) {
                return 0;
            }
            if(o1.getStartTime() == null && o2.getStartTime() != null) {
                return 1;
            }
            if(o1.getStartTime() != null && o2.getStartTime() == null) {
                return -1;
            }
            return o1.getStartTime().get().compareTo(o2.getStartTime().get());
        }
    });

        @Override
        public Task getTask(long id) {
            Task task = null;
            if (tasks.containsKey(id)) {
                task = tasks.get(id);
                historyManager.add(task.getId());
            } else {
                System.out.println("такого нет");
            }
            return task;
        }

        @Override
        public Epic getEpic(long id) {
            Epic epic = null;
            if (epics.containsKey(id)) {
                epic = epics.get(id);
                historyManager.add(epic.getId());
            } else {
                System.out.println("такого нет");
            }
            return epic;
        }

        @Override
        public Subtask getSubtask(long id) {
            Subtask subtask = null;
            if (subtasks.containsKey(id)) {
                subtask = subtasks.get(id);
                historyManager.add(subtask.getId());
            } else {;
                System.out.println("такого нет");
            }
            return subtask;
        }

        @Override
        public void addTask(Task task) throws ManagerSaveException {
            if (task.getId() == 0){
                task.setId(globalId);
                globalId++;
            }
            prioritizedTasks.add(task);
            if(!hasIntersections()) {
                tasks.put(task.getId(), task);
            } else {
                prioritizedTasks.remove(task);
                System.out.println("[Add Task] Задачи пересекаются");
            }
        }

        @Override
        public void addEpic(Epic epic) throws ManagerSaveException {
            if (epic.getId() == 0) {
                epic.setId(globalId);
                globalId++;
            }
            epics.put(epic.getId(), epic);
        }

        @Override
        public void addSubtask(Subtask subtask, long epicId) throws ManagerSaveException {
            if (epics.containsKey(epicId)) {
                if (subtask.getId() == 0) {
                    subtask.setId(globalId);
                    globalId++;
                }
                prioritizedTasks.add(subtask);
                if(!hasIntersections()) {
                    subtask.setParentId(epicId);
                    subtasks.put(subtask.getId(), subtask);
                    Epic epic = epics.get(epicId);
                    List<Long> epicSubtasks = epic.getSubtasks();
                    epicSubtasks.add(subtask.getId());
                    epic.setSubtasks(epicSubtasks);
                    updateEpic(epic);
                } else {
                    prioritizedTasks.remove(subtask);
                    System.out.println("[Add Subtasks] Подзадачи пересекаются");
                }

            } else {
                System.out.println("Для создания подзадачи нужен эпик.");
            }
        }

        @Override
        public void clearTask() throws ManagerSaveException {
            tasks.clear();
        }

        @Override
        public void clearEpic() throws ManagerSaveException {
            epics.clear();
            subtasks.clear();
        }

        @Override
        public void clearSubtask() throws ManagerSaveException {
            subtasks.clear();
            for(Epic epic : epics.values()){
                updateEpic(epic);
            }
        }

        @Override
        public void removeTask(long id) throws ManagerSaveException {
            if (tasks.containsKey(id)) {
                tasks.remove(id);
                historyManager.remove(id);
                System.out.println("Задача удалена.");
            } else {
                System.out.println("Такой задачи нет.");
            }
        }

        @Override
        public void removeEpic(long id) throws ManagerSaveException {
            if (epics.containsKey(id)) {
                HashSet<Long> set = new HashSet<>();
                for (Subtask sub : subtasks.values()){
                        if (id == sub.getParentId()) {
                            set.add(sub.getId());
                            historyManager.remove(sub.getId());
                        }
                }
                subtasks.keySet().removeAll(set);
                epics.remove(id);
                historyManager.remove(id);
                System.out.println("Эпик удален.");
            } else {
                System.out.println("Такого эпика нет.");
            }
        }

        @Override
        public void removeSubtask(long id) throws ManagerSaveException {
            if (subtasks.containsKey(id)) {
                subtasks.remove(id);
                historyManager.remove(id);
                System.out.println("Подзадача удалена.");
            } else {
                System.out.println("Такой подзадачи нет.");
            }
        }

        @Override
        public List<Subtask> getEpicSubtasks (long id) {
            List<Subtask> subtasksList = new ArrayList<>();
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

        @Override
        public List<Task> getTaskList() {
            List<Task> taskList = new ArrayList<>();
                for (Task tasks : tasks.values()) {
                    taskList.add(tasks);
                }
            return taskList;
        }

        @Override
        public List<Epic> getEpicList() {
            List<Epic> epicList = new ArrayList<>();
                for (Epic epics : epics.values()) {
                    epicList.add(epics);
                }
            return epicList;
        }

        @Override
        public List<Subtask> getSubtaskList() {
            List<Subtask> subtaskList = new ArrayList<>();
                for (Subtask subtasks : subtasks.values()) {
                    subtaskList.add(subtasks);
                }
            return subtaskList;
        }

        @Override
        public void updateTask(Task task) throws ManagerSaveException {
            if(tasks.containsKey(task.getId())) {
                prioritizedTasks.remove(task);
                prioritizedTasks.add(task);
                if(!hasIntersections()) {
                    tasks.put(task.getId(), task);
                } else {
                    prioritizedTasks.remove(task);
                    System.out.println("[Update Task] Задачи пересекаются");
                }
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        @Override
        public void updateEpic(Epic epic) throws ManagerSaveException {
            if(epics.containsKey(epic.getId())) {
                epic.setStatus(getEpicStatus(epic.getId()));
                epic.setStartTime(getEpicStartTime(epic.getId()));
                epic.setDuration(getEpicDuration(epic.getId()));
                epics.put(epic.getId(), epic);
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        @Override
        public void updateSubtask(Subtask subtask) throws ManagerSaveException {
            if(subtasks.containsKey(subtask.getId())) {
                prioritizedTasks.remove(subtask);
                prioritizedTasks.add(subtask);
                if(!hasIntersections()) {
                    subtasks.put(subtask.getId(), subtask);
                    Epic epic = epics.get(subtask.getParentId());
                    updateEpic(epic);
                } else {
                    prioritizedTasks.remove(subtask);
                    System.out.println("[Update Subtask] Подзадачи пересекаются");
                }
            } else {
                System.out.println("вам нечего обновлять");
            }
        }

        @Override
        public Status getEpicStatus(long id) {
           Status status = Status.NEW;
           List<Subtask> sub = getEpicSubtasks(id);
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


        @Override
        public List<Long> history() throws ManagerSaveException {
            return historyManager.getHistory();
        }

        @Override
        public void clearHistory() throws ManagerSaveException {
            historyManager.clearAll();
        }

        @Override
        public long getEpicDuration(long id){
            long result = 0;
            List<Subtask> subtasks = getEpicSubtasks(id);
            for(Subtask sub : subtasks) {
                result += sub.getDuration();
            }
            return result;
        }

        @Override
        public Optional<LocalDateTime> getEpicStartTime(long id) {
            List<Subtask> subtasks = getEpicSubtasks(id);
            if(subtasks.size() == 0) {
                return Optional.empty();
            }
            Optional<LocalDateTime> result = subtasks.get(0).getStartTime();
            for(Subtask sub : subtasks) {
                if(sub.getStartTime() != null && sub.getStartTime().isPresent()){
                    if(result == null) {
                        result = sub.getStartTime();
                        continue;
                    }
                    if(sub.getStartTime().get().isBefore(result.get())){
                        result = sub.getStartTime();
                    }
                }
            }
            return result;
        }

        public TreeSet<Task> getPrioritizedTasks(){
            return prioritizedTasks;
        }

       public boolean hasIntersections() {
            boolean result = false;
            Task previousTask = null;
            for(Task task : prioritizedTasks) {
                if(previousTask == null) {
                    previousTask = task;
                    continue;
                }
                if(previousTask.getEndTime() == null || task.getStartTime() == null) {
                    continue;
                }
                if (previousTask.getEndTime().get().isAfter(task.getStartTime().get())) {
                    result = true;
                    break;
                }
                previousTask = task;
            }
            return result;
        }
}
