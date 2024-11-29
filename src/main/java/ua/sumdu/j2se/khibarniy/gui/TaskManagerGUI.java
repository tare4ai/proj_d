package ua.sumdu.j2se.khibarniy.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ua.sumdu.j2se.khibarniy.tasks.AbstractTaskList;
import ua.sumdu.j2se.khibarniy.tasks.ArrayTaskList;
import ua.sumdu.j2se.khibarniy.tasks.Task;
import ua.sumdu.j2se.khibarniy.tasks.TaskIO;

public class TaskManagerGUI {
    private static final Logger logger = Logger.getLogger(TaskManagerGUI.class);
    private JFrame frame;
    private AbstractTaskList taskList;
    private TaskInputPanel taskInputPanel;
    private TaskListPanel taskListPanel;
    private FileOperationPanel fileOperationPanel;
    private EditPanel editPanel;
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/J2SE";
    private static final String DATABASE_USER = "postgres"; // Задайте ім'я користувача
    private static final String DATABASE_PASSWORD = "admin"; // Задайте пароль
    private Connection connection;
    private TaskToDatabase taskToDatabase = new TaskToDatabase();  // Ініціалізація роботи з базою даних
    
    public TaskManagerGUI() {
        logger.info("Task Manager GUI initialized.");
        try {
        taskList = new ArrayTaskList();  // Використовуємо ArrayTaskList для прикладу
        // Ініціалізація інтерфейсу
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        fileOperationPanel = new FileOperationPanel(this);
        taskInputPanel = new TaskInputPanel(this);
        taskListPanel = new TaskListPanel();
        editPanel = new EditPanel(this);
        

        JPanel mainPanel = new JPanel();
        frame.setContentPane(mainPanel);
        
       

        // Додайте панель введення задач
        mainPanel.add(taskInputPanel);
        mainPanel.add(taskListPanel);
        mainPanel.add(fileOperationPanel);
        mainPanel.add(editPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Підключення до бази даних
        connectToDatabase();
        
        loadTasksFromDatabase();
        // Додавання обробника для закриття вікна
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveTasksToDatabase();
                disconnectFromDatabase();
                logger.info("Application closed.");
            }
        });
        } catch (Exception e) {
            logger.error("Error during initialization", e);
        }
}

    // Підключення до бази даних
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            logger.info("Connection to PostgreSQL database established.");
        } catch (SQLException e) {
            logger.error("Failed to connect to the database.", e);
            JOptionPane.showMessageDialog(frame, "Failed to connect to the database.");
        }
    }

    private void loadTasksFromDatabase() {
        List<Task> tasks = taskToDatabase.loadTasksFromDatabase();
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tasks found in the database.");
            return; // Якщо список порожній, виходимо
        }
        for (Task task : tasks) {
            taskList.add(task);
            taskListPanel.getTaskListModel().addElement(task);
        }
    }

    // Відключення від бази даних
    private void disconnectFromDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Disconnected from PostgreSQL database.");
            }
        } catch (SQLException e) {
            logger.error("Failed to disconnect from the database.", e);
        }
    }
    
    private void saveTasksToDatabase() {
        taskToDatabase.saveTasksToDatabase(taskList);  // Зберігаємо задачі з taskList в базу
    }

    
    // Метод для додавання задачі
    public void addTask(String title, String time) {
        try {
            LocalDateTime taskTime = LocalDateTime.parse(time);
            Task task = new Task(title, taskTime);
            task.setActive(true);
    
            // Додаємо задачу до моделі
            taskListPanel.getTaskListModel().addElement(task);
            taskList.add(task); // Зберігаємо задачу у внутрішньому списку
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please use the correct format.");
            logger.error("Error message", e);
        }
    }
    
    // Метод для збереження задач у файл
    public void saveTasks() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Tasks");
            
            // Фільтри файлів
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Binary files (*.bin)", "bin"));
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON files (*.json)", "json"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            
            int result = fileChooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                 // Перевірка вибраного типу файлу
                String description = fileChooser.getFileFilter().getDescription();
                String filePath = file.getAbsolutePath();
                filePath = filePath.replace("\\", "/");
                // Додаємо розширення, якщо його немає
                if (description.contains("Binary") && !filePath.endsWith(".bin")) {
                    file = new File(filePath + ".bin");
                    TaskIO.writeBinary(taskList, file); // Збереження у бінарний файл
                } else if (description.contains("JSON") && !filePath.endsWith(".json")) {
                    file = new File(filePath + ".json");
                    TaskIO.writeText(taskList, file); // Збереження у JSON
                } else {
                    JOptionPane.showMessageDialog(frame, "Unsupported file format. Use .bin, .json, or .txt.");
                    return;
                }

                JOptionPane.showMessageDialog(frame, "Tasks saved successfully.");
        }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving tasks: " + e.getMessage());
            logger.error("Error message", e);
        }
    }
    

    // Метод для завантаження задач з файлу
    public void loadTasks() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load Tasks");
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                if (filePath.endsWith(".bin")){
                TaskIO.readBinary(taskList, file);  // Завантажуємо задачі з бінарного файлу
                }
                if (filePath.endsWith(".json")){
                    TaskIO.readText(taskList,file);
                }
                
                taskListPanel.updateTaskList(taskList);  // Оновлюємо список задач
                JOptionPane.showMessageDialog(frame, "Tasks loaded successfully.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading tasks.");
            logger.error("Error message", e);
        }
    }

    // Метод для редагування вибраної задачі
    public void editSelectedTask() {
        int selectedIndex = taskListPanel.getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = taskListPanel.getTaskListModel().get(selectedIndex);
    
            String newTitle = JOptionPane.showInputDialog(frame, "Enter new title:", selectedTask.getTitle());
            if (newTitle != null && !newTitle.isBlank()) {
                selectedTask.setTitle(newTitle);
            }
    
            String newTime = JOptionPane.showInputDialog(frame, "Enter new time (yyyy-MM-ddTHH:mm):", selectedTask.getTime().toString());
            if (newTime != null) {
                try {
                    LocalDateTime parsedTime = LocalDateTime.parse(newTime);
                    selectedTask.setTime(parsedTime);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid time format. Please use yyyy-MM-ddTHH:mm.");
                }
            }
    
            int result = JOptionPane.showConfirmDialog(frame, "Is this task active?", "Task Active Status",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            selectedTask.setActive(result == JOptionPane.YES_OPTION);
    
            taskListPanel.getTaskListModel().set(selectedIndex, selectedTask); // Оновлюємо задачу в моделі
        } else {
            JOptionPane.showMessageDialog(frame, "No task selected.");
        }
    }

// Метод для видалення вибраної задачі
    public void deleteSelectedTask() {
        int selectedIndex = taskListPanel.getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = taskListPanel.getTaskListModel().get(selectedIndex);

            DefaultListModel<Task> model = taskListPanel.getTaskListModel();
            model.removeElement(selectedTask); // Видаляємо із моделі
            taskList.remove(selectedTask); // Видаляємо із списку задач
        } else {
            JOptionPane.showMessageDialog(frame, "No task selected.");
        }
    }

    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
    

    // Запуск GUI
    public void display() {
        frame.setVisible(true);
    }

   
}
