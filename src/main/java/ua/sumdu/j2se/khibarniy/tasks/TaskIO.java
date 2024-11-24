package ua.sumdu.j2se.khibarniy.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ua.sumdu.j2se.khibarniy.tasks.adapter.*;

import java.time.LocalDateTime;
import java.io.*;
import java.util.List;
import java.lang.reflect.Type;

public class TaskIO {

    // Запис задач у бінарному потік
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(out)) {
            List<Task> taskList = tasks.getTasks();
            dataOut.writeInt(taskList.size());  // Кількість задач
            for (Task task : taskList) {
                dataOut.writeUTF(task.getTitle()); // Назва
                dataOut.writeLong(task.getTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()); // Час
            }
        }
    }

    // Зчитування задач з бінарного потоку
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            int size = dataIn.readInt();
            for (int i = 0; i < size; i++) {
                String title = dataIn.readUTF();
                long timeMillis = dataIn.readLong();
                Task task = new Task(title, LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(timeMillis), java.time.ZoneId.systemDefault()));
                tasks.add(task);
            }
        }
    }

    // Запис задач у файл в бінарному форматі
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        if (!file.canWrite()) throw new IOException("Файл недоступний для запису.");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            write(tasks, fileOut);
        }
    }

    // Зчитування задач з файлу в бінарному форматі
    public static void readBinary(AbstractTaskList tasks, File file) throws IOException {
        if (!file.canRead()) throw new IOException("Файл недоступний для запису.");
        try (FileInputStream fileIn = new FileInputStream(file)) {
            read(tasks, fileIn);
        }
    }
    // Запис задач у потік в форматі JSON
    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())  // Реєстрація адаптера
        .create();
        gson.toJson(tasks.getTasks(), out);
    }

    // Зчитування задач із потоку в список
    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())  // Реєстрація адаптера
        .create();
        Type listType = new TypeToken<List<Task>>() {}.getType();
        List<Task> taskList = gson.fromJson(in, listType);
        taskList.forEach(tasks::add);
    }

    // Запис задач у файл в форматі JSON
    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        if (!file.canWrite()) throw new IOException("Файл недоступний для запису.");
        try (FileWriter fileWriter = new FileWriter(file)) {
            write(tasks, fileWriter);
        }
    }

    // Зчитування задач із файлу в форматі JSON
    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        if (!file.canRead()) throw new IOException("Файл недоступний для запису.");
        try (FileReader fileReader = new FileReader(file)) {
            read(tasks, fileReader);
        }
    }
}