package grocerystore.services.concrete;

import grocerystore.domain.abstracts.IRepositoryListGrocery;
import grocerystore.domain.entities.Grocery;
import grocerystore.domain.entities.ListGrocery;
import grocerystore.domain.entities.Order;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.abstracts.IListGroceryService;
import grocerystore.services.exceptions.ListGroceryServiceException;
import grocerystore.services.models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by raxis on 29.12.2016.
 * Работа со списком продуктов в заказе
 */
@Service
public class ListGroceryService implements IListGroceryService {
    private static final Logger logger = LoggerFactory.getLogger(ListGroceryService.class);

    private IRepositoryListGrocery listGroceryHandler;

    public ListGroceryService(IRepositoryListGrocery listGroceryHandler){
        this.listGroceryHandler=listGroceryHandler;
    }


    /**
     * Создание списка продуктов соответствующих заказу
     * @param cart
     * @param order
     * @throws DAOException
     */
    @Override
    public void createListGrocery(Cart cart, Order order) throws ListGroceryServiceException {
        for(Map.Entry entry : cart.getMap().entrySet()){
            ListGrocery listGrocery = new ListGrocery();
            listGrocery.setId(order.getGrocerylistid());
            listGrocery.setGroceryId(((Grocery)entry.getKey()).getId());
            listGrocery.setQuantity((int)entry.getValue());

            try {
                listGroceryHandler.create(listGrocery);
            } catch (DAOException e) {
                logger.error("cant create",e);
                throw new ListGroceryServiceException("Невозможно завершить формирование корзины!",e);
            }
        }
    }
}
