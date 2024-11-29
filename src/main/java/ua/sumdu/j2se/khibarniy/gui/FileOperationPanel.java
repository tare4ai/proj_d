package ua.sumdu.j2se.khibarniy.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import ua.sumdu.j2se.khibarniy.tasks.*;

public class FileOperationPanel extends JPanel {

    private JButton saveButton;
    private JButton loadButton;
    private TaskManagerGUI parent;

    public FileOperationPanel(TaskManagerGUI parent) {
        this.parent = parent;
        setLayout(new FlowLayout());

        saveButton = new JButton("Save Tasks");
        loadButton = new JButton("Load Tasks");

        add(saveButton);
        add(loadButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.saveTasks();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.loadTasks();
            }
        });
    }
}
