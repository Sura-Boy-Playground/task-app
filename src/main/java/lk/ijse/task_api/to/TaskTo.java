package lk.ijse.task_api.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskTo {
    @NotNull(groups = Update.class, message = "Task id can't be empty")
    private Integer id;
    @NotBlank(message = "Task description can't be empty")
    private String description;
    @NotNull(message = "Task status can't be null")
    private Boolean completed;

    public TaskTo(String description, Boolean completed) {
        this.description = description;
        this.completed = completed;
    }

    public interface Update extends Default{}
}
