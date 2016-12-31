package Services.Exceptions;

import Services.Models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 30.12.2016.
 */
public class FormUserException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(FormUserException.class);

    private Message message;

    public FormUserException(Message message){
        this.message=message;
    }

    public Message getExceptionMessage() {
        return message;
    }
}
