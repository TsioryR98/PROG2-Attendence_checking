package Models.exception;

public class BadRequestException extends ExceptionAPI{

    public BadRequestException(String message) {
        super(TypeOfException.CLIENT_ERROR, message);
    }
    public BadRequestException(String message, Exception source) {
        super(TypeOfException.CLIENT_ERROR, source);
    }
}
