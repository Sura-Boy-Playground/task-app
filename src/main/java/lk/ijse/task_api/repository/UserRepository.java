package lk.ijse.task_api.repository;

import lk.ijse.task_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
