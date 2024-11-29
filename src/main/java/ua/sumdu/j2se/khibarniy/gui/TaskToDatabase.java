package ua.sumdu.j2se.khibarniy.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ua.sumdu.j2se.khibarniy.tasks.AbstractTaskList;
import ua.sumdu.j2se.khibarniy.tasks.Task;

public class TaskToDatabase {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/J2SE";

    // Збереження задач у базу даних
    public void saveTasksToDatabase(AbstractTaskList tasks) {
        String truncSQL = "TRUNCATE TABLE tasks;";
        String insertSQL = "INSERT INTO tasks (title, time, active) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, "postgres", "admin");
            PreparedStatement pstmt = conn.prepareStatement(insertSQL); PreparedStatement ts = conn.prepareStatement(truncSQL)) {
                 // Використовуємо PreparedStatement
                 ts.executeUpdate();
            for (Task task : tasks) {
                pstmt.setString(1, task.getTitle());
                pstmt.setString(2, task.getTime().toString());  // Перетворення LocalDateTime у String
                pstmt.setBoolean(3, task.isActive());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Завантаження задач з бази даних
    public List<Task> loadTasksFromDatabase() {
        List<Task> tasks = new ArrayList<>();
        String selectSQL = "SELECT title, time, active FROM tasks";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, "postgres", "admin");
             Statement stmt = conn.createStatement();  // Використовуємо Statement
             ResultSet rs = stmt.executeQuery(selectSQL)) {  // Виконання запиту
            while (rs.next()) {
                String title = rs.getString("title");
                LocalDateTime time = LocalDateTime.parse(rs.getString("time"));
                tasks.add(new Task(title, time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}