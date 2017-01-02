package grocerystore.Services.Concrete;

import grocerystore.Domain.Abstract.IRepositoryGrocery;
import grocerystore.Domain.Abstract.IRepositoryListGrocery;
import grocerystore.Domain.Concrete.ListGrocerySql;
import grocerystore.Domain.Concrete.GrocerySql;
import grocerystore.Domain.Entities.Grocery;
import grocerystore.Domain.Entities.ListGrocery;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IGroceryService;
import grocerystore.Services.Models.Message;
import javafx.util.converter.BigDecimalStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 * Работа с продуктами
 */
@Component
public class GroceryService implements IGroceryService {
    private static final Logger logger = LoggerFactory.getLogger(GroceryService.class);

    @Autowired
    private IRepositoryGrocery groceryHandler;
    @Autowired
    private IRepositoryListGrocery listGroceryHandler;

    public GroceryService(IRepositoryGrocery groceryHandler, IRepositoryListGrocery listGroceryHandler){
        this.groceryHandler = groceryHandler;
        this.listGroceryHandler = listGroceryHandler;
    }


    @Override
    public List<Grocery> getGroceryList() throws DAOException {
        return groceryHandler.getAll();
    }

    @Override
    public Grocery getGrocery(String groceryid) throws DAOException {
        return groceryHandler.getOne(UUID.fromString(groceryid));
    }

    @Override
    public void groceryCreate(String name, String price, String quantity) throws DAOException {
        Grocery grocery = new Grocery();

        grocery.setId(UUID.randomUUID());
        grocery.setIscategory(false);
        grocery.setParentid(new UUID(0L,0L));
        grocery.setName(name);
        grocery.setPrice(new BigDecimalStringConverter().fromString(price));
        grocery.setQuantity(Integer.parseInt(quantity));

        groceryHandler.create(grocery);
    }

    @Override
    public void groceryDelete(String groceryid) throws DAOException {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));
        List<ListGrocery> listGroceries = listGroceryHandler.getListByGroceryId(grocery.getId());

        groceryHandler.delete(grocery.getId());

        for(ListGrocery gl : listGroceries){
            listGroceryHandler.delete(gl.getId());
        }
    }

    @Override
    public Message groceryUpdate(String groceryid, String name, String price, String quantity) throws DAOException {
        Grocery grocery = groceryHandler.getOne(UUID.fromString(groceryid));

        if(grocery!=null){
            grocery.setName(name);
            grocery.setPrice(new BigDecimalStringConverter().fromString(price));
            grocery.setQuantity(Integer.parseInt(quantity));

            groceryHandler.update(grocery);

            return new Message("Изменения успешно сохранены!", Message.Status.OK);
        }

        return new Message("Продукт не найден в базе!", Message.Status.ERROR);
    }
}
