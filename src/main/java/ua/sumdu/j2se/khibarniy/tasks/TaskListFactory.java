package ua.sumdu.j2se.khibarniy.tasks;

public class TaskListFactory {

    public static AbstractTaskList createTaskList(ListTypes type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default:
                throw new IllegalArgumentException("Unknown task list type");
        }
    }
}
