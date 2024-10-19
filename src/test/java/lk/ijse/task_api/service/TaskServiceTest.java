package lk.ijse.task_api.service;

import jakarta.transaction.Transactional;
import lk.ijse.task_api.service.exception.ServiceException;
import lk.ijse.task_api.to.TaskTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new TestingAuthenticationToken("asiri", "whatever"));
        SecurityContextHolder.setContext(context);
    }

    @ValueSource(strings = {"Implement Repos", "Implement Services", "Test Services"})
    @ParameterizedTest
    void createTask(String description) {
        assertDoesNotThrow(() -> {
            System.out.println(saveTask(description));
        });
    }

    private TaskTo saveTask(String description) {
        TaskTo taskTo = new TaskTo(description, false);
        return taskService.createTask(taskTo);
    }

    @Test
    void updateTask() {
        TaskTo taskTo = saveTask("New Task");
        taskTo.setCompleted(true);
        taskTo.setDescription("New Description");
        assertDoesNotThrow(() -> {
            taskService.updateTask(taskTo);
        });
        assertThrows(ServiceException.class, () -> {
            taskService.deleteTask(-10);
        });
    }

    @Test
    void deleteTask() {
        TaskTo taskTo = saveTask("New Task");
        assertDoesNotThrow(() -> {
            taskService.deleteTask(taskTo.getId());
        });
        assertThrows(ServiceException.class, () -> {
            taskService.deleteTask(-10);
        });
    }

    @Test
    void getAllTasks() {
        saveTask("New Task1");
        saveTask("New Task2");
        saveTask("New Task3");
        assertDoesNotThrow(() -> {
            List<TaskTo> allTasks = taskService.getAllTasks();
            allTasks.forEach(System.out::println);
            assertTrue(allTasks.size() >= 3);
        });
    }
}