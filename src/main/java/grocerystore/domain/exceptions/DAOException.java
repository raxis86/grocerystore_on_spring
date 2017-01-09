package grocerystore.Domain.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 30.12.2016.
 */
public class DAOException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(DAOException.class);

    public DAOException(String message){
        super(message);
    }
}
