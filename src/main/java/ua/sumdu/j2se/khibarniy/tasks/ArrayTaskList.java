package ua.sumdu.j2se.khibarniy.tasks;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayTaskList extends AbstractTaskList {

    private Task[] tasks;
    private int size;

    public ArrayTaskList() {
        tasks = new Task[10];
        size = 0;
    }

    @Override
    public AbstractTaskList createList() {
        return new ArrayTaskList(); // Повертає новий список типу ArrayTaskList
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Task next() {
                return tasks[index++];
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    public void add(Task task) {
        if (size == tasks.length) {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
        }
        tasks[size++] = task;
    }

    @Override
    public AbstractTaskList clone() {
        ArrayTaskList clonedList = new ArrayTaskList();
        for (Task task : this) {
            clonedList.add(task.clone());
        }
        return clonedList;
    }
}