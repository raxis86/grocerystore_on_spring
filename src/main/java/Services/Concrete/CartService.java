package Services.Concrete;

import Domain.Abstract.IRepositoryGrocery;
import Domain.Concrete.GrocerySql;
import Domain.Entities.Grocery;
import Domain.Exceptions.DAOException;
import Services.Abstract.ICartService;
import Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 * Класс для работы с корзиной покупок
 */
public class CartService implements ICartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private IRepositoryGrocery groceryHandler;

    public CartService(){
        this.groceryHandler=new GrocerySql();
    }

    /**
     * Добавление продукта в корзину по коду продукта
     * @param cart - объект корзина
     * @param groceryid - код продукта
     */
    @Override
    public void addToCart(Cart cart, String groceryid) throws DAOException {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        if(grocery!=null){
            cart.addItem(grocery,1);
        }
    }

    /**
     * Удаление продукта из корзины по коду продукта
     * @param cart
     * @param groceryid
     */
    @Override
    public void removeFromCart(Cart cart, String groceryid) throws DAOException {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        if(grocery!=null){
            cart.removeItem(grocery);
        }
    }
}
