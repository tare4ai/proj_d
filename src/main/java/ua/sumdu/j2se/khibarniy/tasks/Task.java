package ua.sumdu.j2se.khibarniy.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String title;
    private boolean isActive;
    private LocalDateTime time;           // для одноразових задач
    private LocalDateTime startTime;      // для повторюваних задач
    private LocalDateTime endTime;        // для повторюваних задач
    private int repeatInterval;           // інтервал для повторюваних задач

    // Конструктор для неповторюваної задачі
    public Task(String title, LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null.");
        }
        this.title = title;
        this.time = time;
        this.isActive = false;
    }

    // Конструктор для повторюваної задачі
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        if (start == null || end == null || interval <= 0) {
            throw new IllegalArgumentException("Start time, end time, and interval must be valid.");
        }
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
        this.isActive = false;
    }

    // Методи для роботи з часом
    public LocalDateTime getTime() {
        return isRepeated() ? startTime : time;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public boolean isRepeated() {
        return repeatInterval > 0;
    }

    public LocalDateTime getNextExecutionTime(LocalDateTime currentTime) {
        if (!isActive) {
            return null;
        }

        if (!isRepeated()) {
            return currentTime.isBefore(time) ? time : null;
        }

        if (currentTime.isBefore(startTime)) {
            return startTime;
        } else if (currentTime.isAfter(endTime)) {
            return null;
        } else {
            LocalDateTime nextExecution = startTime.plusSeconds(((currentTime.toEpochSecond(java.time.ZoneOffset.UTC) - startTime.toEpochSecond(java.time.ZoneOffset.UTC)) / repeatInterval) * repeatInterval);
            return nextExecution.isBefore(endTime) ? nextExecution : null;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Task clone() {
        if (isRepeated()) {
            return new Task(title, startTime, endTime, repeatInterval);
        }
        return new Task(title, time);
    }

    @Override
    public String toString() {
        if (time != null) {
            return "Task{title='" + title + "', time=" + time + "}";
        } else if (startTime != null && endTime != null) {
            return "Task{title='" + title + "', startTime=" + startTime + ", endTime=" + endTime + ", interval=" + repeatInterval + " seconds}";
        }
        return "Task{title='" + title + "', time=unknown}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return repeatInterval == task.repeatInterval &&
                Objects.equals(title, task.title) &&
                Objects.equals(time, task.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, repeatInterval);
    }
}