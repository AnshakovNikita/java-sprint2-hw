package ru.yandex.practicum.manager;

import ru.yandex.practicum.tracker.Task;

import java.util.ArrayList;
import java.util.List;

public class LinkedHistoryList<T> {
    private Node head;
    private Node tail;
    private int size = 0;



    class Node {
        private Task data;
        private Node next;
        private Node prev;

        public Node(Task data) {
            this.data = data;
        }
    }

    public Node linkLast(Task item) {

        Node newNode = new Node(item);


        if (head == null) {
            head = newNode;
            tail = newNode;
            head.prev = null;
            tail.next = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
        size++;
        return newNode;
    }

    void removeNode(Node del) {
        if (head == null || del == null) {
            return;
        }

        if (head == del) {
            head = del.next;
        }

        if (del.next != null) {
            del.next.prev = del.prev;
        }

        if (del.prev != null) {
            del.prev.next = del.next;
        }
        size--;
    }

    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        Node current = head;

        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public Task getFirst() {
        return head.data;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public void setSize(int size) {
        this.size = size;
    }
}