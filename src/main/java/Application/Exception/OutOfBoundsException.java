package Application.Exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OutOfBoundsException extends Exception {
    public OutOfBoundsException(String message) {
        super(message);
    }
}
