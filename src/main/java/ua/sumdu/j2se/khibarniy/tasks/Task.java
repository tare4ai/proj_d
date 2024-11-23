package ua.sumdu.j2se.khibarniy.tasks;

import java.util.Objects;

public class Task {
// Поля класу для зберігання даних про задачу
private String title;          // Назва задачі
private boolean isActive;      // Статус задачі (активна/неактивна)
private int time;              // Час виконання (для неповторюваних задач)
private int startTime;         // Час початку (для повторюваних задач)
private int endTime;           // Час завершення (для повторюваних задач)
private int repeatInterval;    // Інтервал повторення (для повторюваних задач)

// Конструктор для неповторюваної задачі (виконання в заданий час)
public Task(String title, int time) {
    if (time < 0) {
        throw new IllegalArgumentException("Time cannot be negative.");
    }
    this.title = title;
    this.time = time;
    this.isActive = false; // Задача за замовчуванням неактивна
}

// Конструктор для повторюваної задачі (виконання в заданому проміжку часу з інтервалом)
public Task(String title, int start, int end, int interval) {
    if (start < 0 || end < 0 || interval <= 0) {
        throw new IllegalArgumentException("Start time, end time, and interval must be non-negative and interval must be greater than 0.");
    }
    this.title = title;
    this.startTime = start;
    this.endTime = end;
    this.repeatInterval = interval;
    this.isActive = false; // Задача за замовчуванням неактивна
}

// Метод для отримання назви задачі
public String getTitle() {
    return title;
}

// Метод для встановлення назви задачі
public void setTitle(String title) {
    this.title = title;
}

// Метод для отримання статусу задачі (активна/неактивна)
public boolean isActive() {
    return isActive;
}

// Метод для встановлення статусу задачі (активна/неактивна)
public void setActive(boolean active) {
    this.isActive = active;
}

// Метод для отримання часу виконання для неповторюваних задач
public int getTime() {
    if (isRepeated()) {
        return startTime; // Якщо задача повторюється, повертаємо час початку
    }
    return time; // Якщо задача не повторюється, повертаємо час виконання
}

// Метод для встановлення часу виконання для неповторюваних задач
public void setTime(int time) {
    if (isRepeated()) {
        // Якщо задача була повторюваною, змінюємо її на неповторювану
        this.startTime = 0;
        this.endTime = 0;
        this.repeatInterval = 0;
    }
    this.time = time; // Встановлюємо час для неповторюваної задачі
}

// Метод для отримання часу початку для повторюваної задачі
public int getStartTime() {
    if (!isRepeated()) {
        return time; // Якщо задача не повторюється, повертаємо час виконання
    }
    return startTime; // Якщо задача повторюється, повертаємо час початку
}

// Метод для отримання часу завершення для повторюваної задачі
public int getEndTime() {
    if (!isRepeated()) {
        return time; // Якщо задача не повторюється, повертаємо час виконання
    }
    return endTime; // Якщо задача повторюється, повертаємо час завершення
}

// Метод для отримання інтервалу повторення для повторюваної задачі
public int getRepeatInterval() {
    if (!isRepeated()) {
        return 0; // Якщо задача не повторюється, повертаємо 0
    }
    return repeatInterval; // Якщо задача повторюється, повертаємо інтервал
}

// Метод для встановлення часу виконання для повторюваної задачі
public void setTime(int start, int end, int interval) {
    if (!isRepeated()) {
        // Якщо задача не повторюється, змінюємо її на повторювану
        this.time = 0; // скидаємо час для неповторюваної задачі
    }
    this.startTime = start;
    this.endTime = end;
    this.repeatInterval = interval;
}

// Метод для перевірки, чи є задача повторюваною
public boolean isRepeated() {
    return repeatInterval > 0; // Якщо інтервал повторення > 0, то задача повторюється
}

// Метод для отримання наступного моменту виконання задачі
public int getNextExecutionTime(int currentTime) {
    // Якщо задача неактивна, вона не виконується
    if (!isActive) {
        return -1; // Повертаємо -1, якщо задача не виконується
    }

    // Якщо задача не повторюється, її наступний момент виконання - це час її виконання
    if (!isRepeated()) {
        // Якщо поточний час менший за час виконання задачі, то вона ще не виконувалась
        if (currentTime < time) {
            return time;
        } else {
            return -1; // Якщо задача вже виконувалась, наступного виконання не буде
        }
    }

    // Якщо задача повторюється
    // Перевіряємо, чи поточний час досяг часу завершення
    if (currentTime < startTime) {
        // Якщо поточний час до початку, задача ще не виконувалась, повертаємо час початку
        return startTime;
    } else if (currentTime > endTime) {
        // Якщо поточний час вже після завершення періоду, то задачі більше не буде
        return -1;
    } else {
        // Якщо поточний час знаходиться між початком і кінцем, обчислюємо наступне виконання
        int nextExecution = startTime + ((currentTime - startTime) / repeatInterval + 1) * repeatInterval;
        // Якщо наступне виконання більше за час завершення, повертаємо -1
        if (nextExecution > endTime) {
            return -1;
        }
        return nextExecution;
    }
}

// Метод для отримання інформації про задачу
 @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Клонується тільки об'єкт Task", e);
        }
    }

    // Переозначення equals і hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return time == task.time &&
                startTime == task.startTime &&
                endTime == task.endTime &&
                repeatInterval == task.repeatInterval &&
                title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, startTime, endTime, repeatInterval);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", isActive=" + isActive +
                ", time=" + time +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", repeatInterval=" + repeatInterval +
                '}';
    }
}

