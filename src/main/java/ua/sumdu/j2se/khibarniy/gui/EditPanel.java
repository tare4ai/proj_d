package ua.sumdu.j2se.khibarniy.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EditPanel extends JPanel {
        private JButton editButton;
        private JButton deleteButton;
        private TaskManagerGUI parent;
        public EditPanel(TaskManagerGUI parent){
            this.parent = parent;

            editButton = new JButton("Edit Task");
            deleteButton = new JButton("Delete Task");

            add(editButton);
            add(deleteButton);
            // Додавання обробника для кнопки "Edit Task"
            editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                parent.editSelectedTask();}
            });

            // Додавання обробника для кнопки "Delete Task"
            deleteButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                parent.deleteSelectedTask();}
            });
        }
        
}
