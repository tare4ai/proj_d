package ua.sumdu.j2se.khibarniy.tasks;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

public class Main {
    public static void main(String[] args) {
        // Створюємо кілька задач для тестування
        Task task1 = new Task("Обід із гарною дівчиною", LocalDateTime.of(2024, 8, 24, 16, 0));
        Task task2 = new Task("Ранкова пробіжка", LocalDateTime.of(2024, 3, 1, 8, 15), LocalDateTime.of(2024, 9, 1, 8, 15), 86400); // Щодня
        Task task3 = new Task("Приймання ліків", LocalDateTime.of(2024, 8, 20, 8, 15), LocalDateTime.of(2024, 8, 28, 8, 15), 43200); // Кожні 12 годин
        Task task4 = new Task("Зустріч з друзями", LocalDateTime.of(2024, 9, 1, 18, 0));

        // Створюємо список задач (наприклад, використовуємо ArrayTaskList)
        AbstractTaskList taskList = new ArrayTaskList();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);

        // Перевірка методу incoming
        LocalDateTime from = LocalDateTime.of(2024, 8, 25, 8, 0);
        LocalDateTime to = LocalDateTime.of(2024, 8, 26, 8, 0);
        
        System.out.println("Задачі, що входять в діапазон з " + from + " по " + to + ":");
        taskList.incoming(from, to).forEach(task -> System.out.println(task));

        // Перевірка методу calendar
        System.out.println("\nКалендар задач з " + from + " по " + to + ":");
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(taskList, from, to);

        for (LocalDateTime date : calendar.keySet()) {
            System.out.println("Дата: " + date);
            for (Task task : calendar.get(date)) {
                System.out.println("    Задача: " + task);
            }
        }

        // Клонування списку задач
        AbstractTaskList clonedList = taskList.clone();
        System.out.println("\nКлонировані задачі:");
        clonedList.forEach(task -> System.out.println(task));
    }
}