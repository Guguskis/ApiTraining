package lt.liutikas.exception;

public class PersonAlreadyExistsException extends Exception {
    public PersonAlreadyExistsException() {
    }

    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}
