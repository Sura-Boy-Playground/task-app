package lk.ijse.task_api.service;

import lk.ijse.task_api.to.TaskTo;

import java.util.List;

public interface TaskService {

    TaskTo createTask(TaskTo taskTo);

    void updateTask(TaskTo taskTo);

    void deleteTask(int taskId);

    List<TaskTo> getAllTasks();
}
