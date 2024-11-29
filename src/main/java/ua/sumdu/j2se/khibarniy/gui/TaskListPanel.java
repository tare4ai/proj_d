package ua.sumdu.j2se.khibarniy.gui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ua.sumdu.j2se.khibarniy.tasks.AbstractTaskList;
import ua.sumdu.j2se.khibarniy.tasks.Task;

public class TaskListPanel extends JPanel {

    private JList<Task> taskListUI;
    private DefaultListModel<Task> taskListModel;

    public TaskListPanel() {
        setLayout(new BorderLayout());
        taskListModel = new DefaultListModel<>();
        taskListUI = new JList<>(taskListModel);
        add(new JScrollPane(taskListUI), BorderLayout.CENTER);
    }

    public void updateTaskList(AbstractTaskList taskList) {
        taskListModel.clear();  // Очищаємо старі дані
        for (Task task : taskList) {
            taskListModel.addElement(task);  // Додаємо задачі до моделі
        }
        
    }

    public DefaultListModel<Task> getTaskListModel() {
        return taskListModel;
    }

    public JList<Task> getTaskListUI() {
        return taskListUI;
    }

    // Отримання вибраного індексу
    public int getSelectedIndex() {
        return taskListUI.getSelectedIndex();
    }
}
