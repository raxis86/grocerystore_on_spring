package grocerystore.Services.Concrete;

import grocerystore.Domain.Abstract.IRepositoryListGrocery;
import grocerystore.Domain.Concrete.ListGrocerySql;
import grocerystore.Domain.Entities.Grocery;
import grocerystore.Domain.Entities.ListGrocery;
import grocerystore.Domain.Entities.Order;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IListGroceryService;
import grocerystore.Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by raxis on 29.12.2016.
 * Работа со списком продуктов в заказе
 */
@Component
public class ListGroceryService implements IListGroceryService {
    private static final Logger logger = LoggerFactory.getLogger(ListGroceryService.class);

    @Autowired
    private IRepositoryListGrocery listGroceryHandler;

    public ListGroceryService(IRepositoryListGrocery listGroceryHandler){
        this.listGroceryHandler=listGroceryHandler;
    }

    /*public ListGroceryService(){
        this.listGroceryHandler = new ListGrocerySql();
    }*/


    /**
     * Создание списка продуктов соответствующих заказу
     * @param cart
     * @param order
     * @throws DAOException
     */
    @Override
    public void createListGrocery(Cart cart, Order order) throws DAOException {
        for(Map.Entry entry : cart.getMap().entrySet()){
            ListGrocery listGrocery = new ListGrocery();
            listGrocery.setId(order.getGrocerylistid());
            listGrocery.setGroceryId(((Grocery)entry.getKey()).getId());
            listGrocery.setQuantity((int)entry.getValue());

            listGroceryHandler.create(listGrocery);
        }
    }
}
