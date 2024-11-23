package ua.sumdu.j2se.khibarniy.tasks;

import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractTaskList implements Iterable<Task> {

    // Абстрактний метод для створення нового списку правильного типу
    public abstract AbstractTaskList createList();

    // Загальний метод для отримання розміру списку
    public abstract int size();

    // Перевизначаємо метод iterator для зручності доступу до елементів
    @Override
    public abstract Iterator<Task> iterator();

    // Абстрактні методи для додавання, видалення та інших операцій (якщо вони є)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTaskList that = (AbstractTaskList) o;
        Iterator<Task> iterator1 = this.iterator();
        Iterator<Task> iterator2 = that.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            if (!iterator1.next().equals(iterator2.next())) {
                return false;
            }
        }
        return !iterator1.hasNext() && !iterator2.hasNext();
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (Task task : this) {
            result = 31 * result + task.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Task> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(", ");
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    // Метод для клонування
    public abstract AbstractTaskList clone();
}