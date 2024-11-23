package ua.sumdu.j2se.khibarniy.tasks;

import java.util.Iterator;

public class LinkedTaskList extends AbstractTaskList {

    private class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedTaskList() {
        head = null;
        size = 0;
    }

    @Override
    public AbstractTaskList createList() {
        return new LinkedTaskList(); // Повертає новий список типу LinkedTaskList
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Task next() {
                Task task = current.task;
                current = current.next;
                return task;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    public void add(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }

    @Override
    public AbstractTaskList clone() {
        LinkedTaskList clonedList = new LinkedTaskList();
        for (Task task : this) {
            clonedList.add(task.clone());
        }
        return clonedList;
    }
}