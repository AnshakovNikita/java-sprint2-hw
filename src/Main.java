
import ru.yandex.practicum.manager.Manager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Status;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;


public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.addTask(new Task("Задача1", "описание1"));
        manager.addTask(new Task("Задача2", "Описание2"));
        manager.addEpic(new Epic("Эпик1", "Описание3"));
        manager.addSubtask(new Subtask("Подзадача1эпик1", "Описание4"), 3);
        manager.addSubtask(new Subtask("Подзадача2эпик1", "Описание5"), 3);
        manager.addEpic(new Epic("Эпик2", "Описание6"));
        manager.addSubtask(new Subtask("Подзадача1эпик2", "Описание7"), 6);

        System.out.println(manager.getEpicList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getSubtaskList());

        Task taskUpdate = manager.getTask(1);
            taskUpdate.name = "обновилиЗадачу1";
            taskUpdate.description = "обновилиОписание";
            taskUpdate.setStatus(Status.IN_PROGRESS);

        manager.updateTask(taskUpdate);


        Epic epicUpdate = manager.getEpic(3);
            epicUpdate.name = "обновилиЭпик1";
            epicUpdate.description = "обновилиОписание";

        manager.updateEpic(epicUpdate);


        Subtask subtaskUpdate = manager.getSubtask(4);
            subtaskUpdate.name = "НоваяПодзадача1эпик1";
            subtaskUpdate.description = "НовоеОписание4";
            subtaskUpdate.setStatus(Status.DONE);

        manager.updateSubtask(subtaskUpdate);

        Subtask subtaskUpdate1 = manager.getSubtask(5);
            subtaskUpdate1.name = "НоваяПодзадача1эпик1";
            subtaskUpdate1.description = "НовоеОписание4";
            subtaskUpdate1.setStatus(Status.DONE);

        manager.updateSubtask(subtaskUpdate1);

        Subtask subtaskUpdate2 = manager.getSubtask(7);
            subtaskUpdate2.name = "НоваяПодзадача1эпик1";
            subtaskUpdate2.description = "НовоеОписание4";
            subtaskUpdate2.setStatus(Status.IN_PROGRESS);

        manager.updateSubtask(subtaskUpdate2);

        System.out.println(manager.getEpicList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getSubtaskList());


        manager.removeTask(1);
        manager.removeEpic(3);

        System.out.println(manager.getEpicList());
        System.out.println(manager.getTaskList());
        System.out.println(manager.getSubtaskList());
    }
}
