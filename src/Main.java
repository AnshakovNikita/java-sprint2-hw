import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Status;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;


public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();


        inMemoryTaskManager.addTask(new Task("Задача1", "описание1"));
        inMemoryTaskManager.addTask(new Task("Задача2", "Описание2"));
        inMemoryTaskManager.addEpic(new Epic("Эпик1", "Описание3"));
        inMemoryTaskManager.addSubtask(new Subtask("Подзадача1эпик1", "Описание4"), 3);
        inMemoryTaskManager.addSubtask(new Subtask("Подзадача2эпик1", "Описание5"), 3);
        inMemoryTaskManager.addEpic(new Epic("Эпик2", "Описание6"));
        inMemoryTaskManager.addSubtask(new Subtask("Подзадача1эпик2", "Описание7"), 6);

        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(inMemoryTaskManager.getSubtaskList());

        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getSubtask(5));
        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getSubtask(5));
        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getSubtask(5));
        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println(inMemoryTaskManager.getTask(2));





        System.out.println("История просмотров: " + inMemoryTaskManager.history());

        Task taskUpdate = inMemoryTaskManager.getTask(1);
            taskUpdate.name = "обновилиЗадачу1";
            taskUpdate.description = "обновилиОписание";
            taskUpdate.setStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.updateTask(taskUpdate);


        Epic epicUpdate = inMemoryTaskManager.getEpic(3);
            epicUpdate.name = "обновилиЭпик1";
            epicUpdate.description = "обновилиОписание";

        inMemoryTaskManager.updateEpic(epicUpdate);


        Subtask subtaskUpdate = inMemoryTaskManager.getSubtask(4);
            subtaskUpdate.name = "НоваяПодзадача1эпик1";
            subtaskUpdate.description = "НовоеОписание4";
            subtaskUpdate.setStatus(Status.DONE);

        inMemoryTaskManager.updateSubtask(subtaskUpdate);

        Subtask subtaskUpdate1 = inMemoryTaskManager.getSubtask(5);
            subtaskUpdate1.name = "НоваяПодзадача1эпик1";
            subtaskUpdate1.description = "НовоеОписание4";
            subtaskUpdate1.setStatus(Status.DONE);

        inMemoryTaskManager.updateSubtask(subtaskUpdate1);

        Subtask subtaskUpdate2 = inMemoryTaskManager.getSubtask(7);
            subtaskUpdate2.name = "НоваяПодзадача1эпик1";
            subtaskUpdate2.description = "НовоеОписание4";
            subtaskUpdate2.setStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.updateSubtask(subtaskUpdate2);

        System.out.println("История просмотров: " + inMemoryTaskManager.history());

        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(inMemoryTaskManager.getSubtaskList());


        inMemoryTaskManager.removeTask(1);
        inMemoryTaskManager.removeEpic(3);

        System.out.println("История просмотров: " + inMemoryTaskManager.history());

        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(inMemoryTaskManager.getSubtaskList());
    }
}
