package ua.sumdu.j2se.khibarniy.tasks;

public class Main {
    public static void main(String[] args) {
        Task task1 = new Task("Test Task", 10); // задача без повторів
        task1.setActive(true);
        
        Task task2 = new Task("Repeat Task", 5, 20, 5); // задача з інтервалом повтору
        task2.setActive(true);
        
        System.out.println(task1.getNextExecutionTime(9)); // 10 (однократне виконання)
        System.out.println(task1.getNextExecutionTime(10)); // -1 (немає більше виконань)
        
        System.out.println(task2.getNextExecutionTime(4)); // 5 (початок повторів)
        System.out.println(task2.getNextExecutionTime(6)); // 10 (наступне виконання через 5)
        System.out.println(task2.getNextExecutionTime(21)); // -1 (за межами інтервалу)
    }
}