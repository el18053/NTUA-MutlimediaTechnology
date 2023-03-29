package Backend.Exceptions;

public class LoserException extends Exception {
    static int code = 200;
    static String message = "Lost";


    public LoserException() {
        super(message);
    }

    public LoserException(Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }
}
