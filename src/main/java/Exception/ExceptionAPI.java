package Exception;

import lombok.Getter;

@Getter
public class ExceptionAPI extends RuntimeException{
    private final TypeOfException typeOfException;

    public enum TypeOfException{
        CLIENT_ERROR,
        SERVER_ERROR
    }
    public ExceptionAPI(TypeOfException typeOfException, String message){
        super(message);
        this.typeOfException=typeOfException;
    }
    public ExceptionAPI(TypeOfException typeOfException, Exception source){
        super(source);
        this.typeOfException=typeOfException;
    }

}
