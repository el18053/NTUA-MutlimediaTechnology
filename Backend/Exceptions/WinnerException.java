package Backend.Exceptions;

public class WinnerException extends Exception {
    static int code = 200;
    static String message = "Won";


    public WinnerException() {
        super(message);
    }

    public WinnerException(Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }
}