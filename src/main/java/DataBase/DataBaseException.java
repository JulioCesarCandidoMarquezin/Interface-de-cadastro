package DataBase;

import java.io.Serial;

public class DataBaseException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    DataBaseException(String msg){
        super(msg);
    }
}
