package ua.khibarniy;

import ua.sumdu.j2se.khibarniy.gui.TaskManagerGUI;

import javax.swing.*;

public class App 
{
    public static void main( String[] args ){
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            TaskManagerGUI gui = new TaskManagerGUI();
            gui.display();  // Відображаємо інтерфейс користувача
        }
    });
}
}
