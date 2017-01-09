package grocerystore.services.abstracts;

import grocerystore.domain.entities.Order;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.exceptions.ListGroceryServiceException;
import grocerystore.services.models.Cart;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IListGroceryService {
    public void createListGrocery(Cart cart, Order order) throws ListGroceryServiceException;
}
