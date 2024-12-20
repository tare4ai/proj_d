package ua.sumdu.j2se.khibarniy.tasks;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    @Override
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
        super.tasks.add(task);
    }

    @Override
    public boolean remove(Task task) {
        if (head == null) {
            return false;
        }

        if (head.task.equals(task)) {
            head = head.next; // Видаляємо перший елемент
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null && !current.next.task.equals(task)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next; // Перемикаємо посилання, щоб пропустити задачу
            size--;
            return true;
        }

        return false;
    }

    @Override
    public Task get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.task;
    }
    
    @Override
    public Stream<Task> getStream() {
        return StreamSupport.stream(new Spliterator<Task>() {
            private Node current = head;

            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Task> action) {
                if (current != null) {
                    action.accept(current.task);
                    current = current.next;
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<Task> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return size;
            }

            @Override
            public int characteristics() {
                return NONNULL | IMMUTABLE;
            }
        }, false);
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