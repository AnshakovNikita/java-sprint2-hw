import ru.yandex.practicum.manager.FileBackedTasksManager;
import ru.yandex.practicum.Exception.ManagerSaveException;
import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.tracker.Epic;
import ru.yandex.practicum.tracker.Subtask;
import ru.yandex.practicum.tracker.Task;

import java.time.LocalDateTime;
import java.util.Optional;


public class Main {
    public static void main(String[] args) throws ManagerSaveException {
            //FileBackedTasksManager.main(args);
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("Задача1", "описание1");
        task1.setDuration(10);
        task1.setStartTime(Optional.of(LocalDateTime.now()));



        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addEpic(new Epic("Эпик1", "Описание3"));

        Subtask subtask1 = new Subtask("Подзадача1эпик1", "Описание4");
        subtask1.setDuration(5);
        subtask1.setStartTime(Optional.of(LocalDateTime.now()));

        Subtask subtask2 = new Subtask("Подзадача1эпик1", "Описание4");
        subtask2.setDuration(30);
        subtask2.setStartTime(Optional.of(LocalDateTime.now().plusMinutes(10)));

        inMemoryTaskManager.addSubtask(subtask1, 2);
        inMemoryTaskManager.addSubtask(subtask2, 2);



        inMemoryTaskManager.getEpic(2);
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getSubtask(3);
        inMemoryTaskManager.history();
    }
}
