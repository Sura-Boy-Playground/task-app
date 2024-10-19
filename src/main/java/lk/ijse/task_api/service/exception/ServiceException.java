package lk.ijse.task_api.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException {

    public enum Type {
        INTERNAL,
        DUPLICATE,
        NOT_FOUND
    }

    private Type type = Type.INTERNAL;

    public ServiceException() {
    }

    public ServiceException(Type type) {
        this.type = type;
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Type type, String message) {
        super(message);
        this.type = type;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Type type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
