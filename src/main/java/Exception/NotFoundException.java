package Exception;

public class NotFoundException extends ExceptionAPI{
    public NotFoundException(String message,Exception source) {
        super(TypeOfException.CLIENT_ERROR, source);
    }
    public NotFoundException(String message) {
        super(TypeOfException.CLIENT_ERROR, message);
    }
}
