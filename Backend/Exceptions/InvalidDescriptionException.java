package Backend.Exceptions;

public class InvalidDescriptionException extends Exception {
    static int code = 101;
    static String message = "InvalidDescriptionException";


    public InvalidDescriptionException() {
        super(message);
    }

    public InvalidDescriptionException(String message) {
        super(message);
    }

    public InvalidDescriptionException(Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }
}
