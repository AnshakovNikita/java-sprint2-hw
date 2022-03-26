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
        inMemoryTaskManager.addSubtask(new Subtask("Подзадача3эпик1", "Описание7"), 3);



        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getSubtask(5));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getEpic(3));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getSubtask(4));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getEpic(6));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println("История просмотров: " + inMemoryTaskManager.history());



        inMemoryTaskManager.removeTask(2);
        System.out.println("История просмотров: " + inMemoryTaskManager.history());
        inMemoryTaskManager.removeEpic(3);
        System.out.println("История просмотров: " + inMemoryTaskManager.history());

    }
}
