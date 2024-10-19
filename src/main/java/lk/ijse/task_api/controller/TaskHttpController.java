package lk.ijse.task_api.controller;

import lk.ijse.task_api.service.TaskService;
import lk.ijse.task_api.to.TaskTo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/tasks")
@RestController
public class TaskHttpController {

    private final TaskService taskService;

    public TaskHttpController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public TaskTo createNewTask(@RequestBody @Validated TaskTo taskTo) {
        return taskService.createTask(taskTo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = "/{taskId:\\d+}", consumes = "application/json")
    public void updateTask(@PathVariable Integer taskId, @RequestBody @Validated(TaskTo.Update.class) TaskTo taskTo) {
        if (taskId.intValue() != taskTo.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Task ID in the payload does not match the Task ID in the URL");
        taskService.updateTask(taskTo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{taskId:\\d+}")
    public void deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping(produces = "application/json")
    public List<TaskTo> getAllTasks() {
        return taskService.getAllTasks();
    }
}

