package com.matty.task_management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TaskManager {
    private static volatile TaskManager instance;

    private Map<String, Task> tasks;
    private Map<String, List<Task>> userTasks;

    private TaskManager() {}

    public static TaskManager getInstance() {
        if (instance == null) {
            synchronized (TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    public void assignTaskToUser(Task task, User user) {
        userTasks.computeIfAbsent(user.getId(), k -> new ArrayList<>()).add(task);
    }

    public  void unAssignTaskFromUser(Task task, User user) {
        userTasks.get(user.getId()).remove(task);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
        assignTaskToUser(task, task.getAssignedUser());
    }

    public void updateTask(Task updatedTask) {
        Task existingTask = tasks.get(updatedTask.getId());
        if (existingTask == null)
            return;

        synchronized (existingTask) {
            existingTask.setDescription(updatedTask.getDescription());
            User prev = existingTask.getAssignedUser();
            User newUser = updatedTask.getAssignedUser();

            if (!prev.equals(newUser)) {
                unAssignTaskFromUser(existingTask, prev);
                assignTaskToUser(existingTask, newUser);
            }
        }

    }
}
