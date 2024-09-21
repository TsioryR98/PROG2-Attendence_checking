package Exception;

public class ServerException extends ExceptionAPI{
    public ServerException( String message) {
        super(TypeOfException.SERVER_ERROR, message);
    }
    public ServerException(String message,Exception source) {
        super(TypeOfException.SERVER_ERROR, source);
    }
}
