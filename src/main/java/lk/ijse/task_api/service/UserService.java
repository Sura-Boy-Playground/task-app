package lk.ijse.task_api.service;

import lk.ijse.task_api.to.UserTo;

public interface UserService {

    void signUp(UserTo userTo);

    String signIn(UserTo userTo);
}
