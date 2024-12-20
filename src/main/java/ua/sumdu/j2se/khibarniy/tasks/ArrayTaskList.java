package ua.sumdu.j2se.khibarniy.tasks;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

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

    @Override
    public boolean remove(Task task) {
        
        for (int i = 0; i < size; i++) {
            if (tasks[i].equals(task)) { // Знаходимо задачу
                System.arraycopy(tasks, i + 1, tasks, i, size - i - 1);  // Зсув елементів
                tasks[--size] = null;  // Очищаємо останній елемент після зсуву
                return true;
            }
        }
        return false;
    }

    // Отримання задачі за індексом
    @Override
    public Task get(int index) {
        if (index >= 0 && index < size) {
            return tasks[index];  // Повертаємо задачу за індексом
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }
    
    @Override
    public void add(Task task) {
        if (size == tasks.length) {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
        }
        this.tasks[size++] = task;
        super.tasks.add(task); 
    }

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(tasks, 0, size);
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