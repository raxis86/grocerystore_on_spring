package grocerystore.Domain.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 30.12.2016.
 */
public class OrderException extends DAOException {
    private static final Logger logger = LoggerFactory.getLogger(OrderException.class);

    public OrderException(String message) {
        super(message);
    }
}
