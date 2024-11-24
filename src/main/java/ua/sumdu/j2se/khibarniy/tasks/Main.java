package ua.sumdu.j2se.khibarniy.tasks;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        // Створюємо список задач
        AbstractTaskList taskList = new ArrayTaskList() {};
        taskList.add(new Task("Обід з друзями", LocalDateTime.of(2024, 8, 25, 12, 0)));
        taskList.add(new Task("Ранкова пробіжка", LocalDateTime.of(2024, 8, 25, 8, 15)));

        // Записуємо задачі в бінарний файл
        File binaryFile = new File("tasks.bin");
        TaskIO.writeBinary(taskList, binaryFile);

        // Зчитуємо задачі з бінарного файлу
        AbstractTaskList newTaskList = new LinkedTaskList() {};
        TaskIO.readBinary(newTaskList, binaryFile);
        System.out.println("Зчитано з бінарного файлу:");
        newTaskList.getTasks().forEach(System.out::println);

        // Записуємо задачі в JSON файл
        File jsonFile = new File("tasks.json");
        TaskIO.writeText(taskList, jsonFile);

        // Зчитуємо задачі з JSON файлу
        AbstractTaskList anotherTaskList = new LinkedTaskList() {};
        TaskIO.readText(anotherTaskList, jsonFile);
        System.out.println("Зчитано з JSON файлу:");
        anotherTaskList.getTasks().forEach(System.out::println);
    }
}