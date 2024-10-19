package lk.ijse.task_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username", nullable = false)
    private User user;

    public Task(String description, boolean completed, User user) {
        this.description = description;
        this.completed = completed;
        this.user = user;
    }
}
