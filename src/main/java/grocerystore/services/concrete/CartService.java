package grocerystore.services.concrete;

import grocerystore.domain.abstracts.IRepositoryGrocery;
import grocerystore.domain.entities.Grocery;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.abstracts.ICartService;
import grocerystore.services.exceptions.CartServiceException;
import grocerystore.services.models.Cart;
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
    public void addToCart(Cart cart, String groceryid) throws CartServiceException {
        Grocery grocery = null;

        try {
            grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        } catch (DAOException e) {
            logger.error("cant grocery.getOne",e);
            throw new CartServiceException("Невозможно добавить продукт в корзину!",e);
        }

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
    public void removeFromCart(Cart cart, String groceryid) throws CartServiceException {
        Grocery grocery = null;
        try {
            grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        } catch (DAOException e) {
            logger.error("cant grocery.getOne",e);
            throw new CartServiceException("Невозможно удалить продукт из корзины!",e);
        }
        if(grocery!=null){
            cart.removeItem(grocery);
        }
    }
}
