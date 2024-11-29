package ua.sumdu.j2se.khibarniy.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class TaskInputPanel extends JPanel {

    private JTextField titleField;
    private JSpinner timeSpinner;
    private JButton addButton;

    private TaskManagerGUI parent;

    public TaskInputPanel(TaskManagerGUI parent) {
        this.parent = parent;
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Task Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Task Time (YYYY-MM-DD HH:MM):"));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(dateModel);
        
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "yyyy-MM-dd HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date()); // Поточний час за замовчуванням
        add(timeSpinner);

        addButton = new JButton("Add Task");
        add(addButton);

         addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                Date selectedDate = (Date) timeSpinner.getValue();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String formattedDate = formatter.format(selectedDate);
            try {
            // Перетворюємо Date у LocalDateTime
            LocalDateTime dateTime = selectedDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
            
            // Передаємо задачу до батьківського класу
            parent.addTask(title, dateTime.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                TaskInputPanel.this,
                "Помилка: невірний формат дати/часу",
                "Помилка",
                JOptionPane.ERROR_MESSAGE
            );
        }
            }
        });
    }
}
