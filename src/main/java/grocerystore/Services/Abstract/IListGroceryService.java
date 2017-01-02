package grocerystore.Services.Abstract;

import grocerystore.Domain.Entities.Order;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Models.Cart;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IListGroceryService {
    public void createListGrocery(Cart cart, Order order) throws DAOException;
}
