package ua.sumdu.j2se.khibarniy.tasks;

public class Main {
    public static void main(String[] args) {
        // Створення задач
        Task task1 = new Task("Task 1", 10);  // Неповторювана задача
        Task task2 = new Task("Task 2", 20);  // Неповторювана задача
        Task task3 = new Task("Task 3", 30);  // Неповторювана задача

        // Створення списків
        AbstractTaskList arrayTaskList = new ArrayTaskList();
        AbstractTaskList linkedTaskList = new LinkedTaskList();

        // Додавання задач до списків
        arrayTaskList.add(task1);
        arrayTaskList.add(task2);
        arrayTaskList.add(task3);

        linkedTaskList.add(task1);
        linkedTaskList.add(task2);
        linkedTaskList.add(task3);

        // Перевірка методу getStream() для ArrayTaskList
        System.out.println("ArrayTaskList Stream:");
        arrayTaskList.getStream()
                .map(Task::getTitle)
                .forEach(System.out::println); // Очікується виведення назв задач

        // Перевірка методу getStream() для LinkedTaskList
        System.out.println("\nLinkedTaskList Stream:");
        linkedTaskList.getStream()
                .map(Task::getTitle)
                .forEach(System.out::println); // Очікується виведення назв задач

        // Приклад фільтрації задач з часом виконання більше 15
        System.out.println("\nFiltered Stream (tasks with time > 15):");
        arrayTaskList.getStream()
                .filter(task -> task.getTime() > 15)
                .map(Task::getTitle)
                .forEach(System.out::println); // Очікується виведення "Task 2" і "Task 3"

        // Приклад сортування задач за часом виконання
        System.out.println("\nSorted Stream (tasks by time):");
        arrayTaskList.getStream()
                .sorted((task1Sorted, task2Sorted) -> Integer.compare(task1Sorted.getTime(), task2Sorted.getTime()))
                .map(Task::getTitle)
                .forEach(System.out::println); // Очікується сортування задач за часом
    }
}