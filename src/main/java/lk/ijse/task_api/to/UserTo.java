package lk.ijse.task_api.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTo implements Serializable {
    @NotBlank(message = "Username can't be empty")
    @Length(min = 5, max = 20, message = "Username must be between 5 and 20 characters long")
    private String username;
    @NotBlank(message = "Password can't be empty")
    @Length(min = 6, message = "Password must be longer than 6 characters")
    private String password;
    @NotBlank(message = "Display name can't be empty", groups = SignUp.class)
    private String displayName;

    public UserTo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public interface SignUp extends Default{}
}
