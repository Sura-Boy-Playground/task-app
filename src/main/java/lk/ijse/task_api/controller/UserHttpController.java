package lk.ijse.task_api.controller;

import lk.ijse.task_api.service.UserService;
import lk.ijse.task_api.to.UserTo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserHttpController {

    private final UserService userService;

    public UserHttpController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/sign-up", consumes = "application/json")
    public void signUp(@RequestBody @Validated(UserTo.SignUp.class) UserTo userTo) {
        userService.signUp(userTo);
    }

    @PostMapping(value = "/sign-in", consumes = "application/json")
    public Map<String, String> signIn(@RequestBody @Validated UserTo userTo) {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("token", userService.signIn(userTo));
        return payload;
    }
}
