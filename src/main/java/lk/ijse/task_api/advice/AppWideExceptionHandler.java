package lk.ijse.task_api.advice;

import lk.ijse.task_api.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class AppWideExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public void handleServiceException(ServiceException exp) {
        exp.initCause(new ResponseStatusException(HttpStatusCode.valueOf(switch (exp.getType()){
            case DUPLICATE -> 400;
            case NOT_FOUND -> 404;
            case INTERNAL -> 500;
        })));
        if (exp.getType() == ServiceException.Type.INTERNAL) log.error(exp.getMessage(), exp);
        throw exp;
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
    public void handleAuthenticationException(AuthenticationException exp) {
        exp.initCause(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        throw exp;
    }

}
