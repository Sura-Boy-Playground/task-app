package lk.ijse.task_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements Serializable {
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user")
    @Setter(AccessLevel.NONE)
    private List<Task> taskList = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public User(String displayName, String username, String password) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
    }

    public User(String displayName, String username, String password, List<Task> taskList) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.taskList = taskList;
        this.taskList.forEach(task -> task.setUser(this));
    }
}
