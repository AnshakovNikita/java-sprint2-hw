package ru.yandex.practicum.manager;

import java.util.ArrayList;

public class LinkedHistoryList<T> {
    public Node<T> head;
    public Node<T> tail;
    private int size = 0;


    class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;
        public Node(T data) {
            this.data = data;
        }
    }

    public Node<T> linkLast(T item) {

        Node newNode = new Node<T>(item);


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

    public ArrayList<T> getTasks() {
        ArrayList<T> result = new ArrayList<T>();
        Node<T> current = head;

        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public T getFirst() {
        return head.data;
    }



}