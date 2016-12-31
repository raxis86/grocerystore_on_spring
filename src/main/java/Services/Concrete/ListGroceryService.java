package Services.Concrete;

import Domain.Abstract.IRepositoryListGrocery;
import Domain.Concrete.ListGrocerySql;
import Domain.Entities.Grocery;
import Domain.Entities.ListGrocery;
import Domain.Entities.Order;
import Domain.Exceptions.DAOException;
import Services.Abstract.IListGroceryService;
import Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by raxis on 29.12.2016.
 * Работа со списком продуктов в заказе
 */
public class ListGroceryService implements IListGroceryService {
    private static final Logger logger = LoggerFactory.getLogger(ListGroceryService.class);

    private IRepositoryListGrocery listGroceryHandler;

    public ListGroceryService(){
        this.listGroceryHandler = new ListGrocerySql();
    }


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
