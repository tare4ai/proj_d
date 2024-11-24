package ua.sumdu.j2se.khibarniy.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Tasks {

    // Метод incoming для пошуку задач у вказаному інтервалі часу
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            // Перевірка, чи попадає задача в період між start і end
            if (!task.getTime().isBefore(start) && !task.getTime().isAfter(end)) {
                result.add(task);
            }
        }
        return result;
    }

    // Метод calendar для створення календаря задач
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();

        for (Task task : tasks) {
            LocalDateTime taskTime = task.getTime();

            // Перевірка, чи задача попадає в період між start і end
            if (!taskTime.isBefore(start) && !taskTime.isAfter(end)) {
                // Додаємо задачу в календар на відповідний час
                calendar.computeIfAbsent(taskTime, k -> new HashSet<>()).add(task);
            }
        }
        
        return calendar;
    }
}
