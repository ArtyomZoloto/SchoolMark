package exceptions;

public class PasswordEncodingException extends RuntimeException {
    public PasswordEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
