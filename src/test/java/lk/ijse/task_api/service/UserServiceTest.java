package lk.ijse.task_api.service;

import jakarta.transaction.Transactional;
import lk.ijse.task_api.service.exception.ServiceException;
import lk.ijse.task_api.to.UserTo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void signUp() {
        UserTo user = new UserTo("xxx-asiri", "asiri123", "Asiri Ekanayaka");
        assertDoesNotThrow(() -> {
            userService.signUp(user);
        });
        assertThrows(ServiceException.class, () -> {
            userService.signUp(user);
        });
    }

    @Test
    void signIn() {
        signUp();
        UserTo user = new UserTo("xxx-asiri", "asiri123");
        assertDoesNotThrow(() -> {
            String token = userService.signIn(user);
            System.out.println(token);
        });
        assertThrows(BadCredentialsException.class, ()->{
            user.setPassword("invalid-password");
            userService.signIn(user);
        });
        assertThrows(UsernameNotFoundException.class, ()->{
            user.setUsername("kasun");
            userService.signIn(user);
        });
    }
}