package Backend.Exceptions;

public class InvalidValueException extends Exception {
    static int code = 102;
    static String message = "InvalidValueException";

    public InvalidValueException() {
        super(message);
    }

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(Throwable cause, String message) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }
}
