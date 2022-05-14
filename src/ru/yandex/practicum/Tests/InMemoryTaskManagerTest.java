package ru.yandex.practicum.Tests;


import ru.yandex.practicum.manager.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    public InMemoryTaskManagerTest() {
        super(new InMemoryTaskManager());
    }
}