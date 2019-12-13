package lt.liutikas.exception;

public class PersonAlreadyExistsException extends Exception {
    public PersonAlreadyExistsException() {
    }

    public PersonAlreadyExistsException(String message) {
        super(message);
    }

    public PersonAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected PersonAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
