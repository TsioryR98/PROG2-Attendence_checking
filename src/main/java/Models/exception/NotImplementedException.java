package Models.exception;

public class NotImplementedException extends ExceptionAPI{
    public NotImplementedException( String message) {
        super(TypeOfException.SERVER_ERROR, message);
    }
    public NotImplementedException(String message,Exception source) {
        super(TypeOfException.SERVER_ERROR, source);
    }
}
