package grocerystore.Services.Concrete;

import grocerystore.Domain.Abstract.IRepositoryGrocery;
import grocerystore.Domain.Concrete.GrocerySql;
import grocerystore.Domain.Entities.Grocery;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.ICartService;
import grocerystore.Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 * Класс для работы с корзиной покупок
 */
@Component
public class CartService implements ICartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private IRepositoryGrocery groceryHandler;

    public CartService(IRepositoryGrocery groceryHandler){
        this.groceryHandler=groceryHandler;
    }

    /*public CartService(){
        this.groceryHandler=new GrocerySql();
    }*/

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
