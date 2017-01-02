package grocerystore.Services.Abstract;

import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Models.Cart;

/**
 * Created by raxis on 29.12.2016.
 */
public interface ICartService {
    public void addToCart(Cart cart, String groceryid) throws DAOException;
    public void removeFromCart(Cart cart, String groceryid) throws DAOException;
}
