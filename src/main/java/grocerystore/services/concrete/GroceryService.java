package grocerystore.services.concrete;

import grocerystore.domain.abstracts.IRepositoryGrocery;
import grocerystore.domain.abstracts.IRepositoryListGrocery;
import grocerystore.domain.entities.Grocery;
import grocerystore.domain.entities.ListGrocery;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.domain.exceptions.ListGroceryException;
import grocerystore.services.abstracts.IGroceryService;
import grocerystore.services.exceptions.FormGroceryException;
import grocerystore.services.exceptions.GroceryServiceException;
import grocerystore.services.models.Message;
import javafx.util.converter.BigDecimalStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    public List<Grocery> getGroceryList() throws GroceryServiceException {
        List<Grocery> groceryList=null;
        try {
            groceryList=groceryHandler.getAll();
        } catch (DAOException e) {
            logger.error("cant getGroceryList ",e);
            throw new GroceryServiceException("Невозможно получить список продуктов!",e);
        }
        return groceryList;
    }

    @Override
    public Grocery getGrocery(String groceryid) throws GroceryServiceException {
        Grocery grocery=null;
        try {
            grocery=groceryHandler.getOne(UUID.fromString(groceryid));
        } catch (DAOException e) {
            logger.error("cant getGrocery",e);
            throw new GroceryServiceException("Продукт не найлен!",e);
        }
        return grocery;
    }

    @Override
    public void groceryCreate(String name, String price, String quantity) throws GroceryServiceException, FormGroceryException {
        Grocery grocery = new Grocery();

        validator(name,price,quantity);

        grocery.setId(UUID.randomUUID());
        grocery.setIscategory(false);
        grocery.setParentid(new UUID(0L,0L));
        grocery.setName(name);
        grocery.setPrice(new BigDecimalStringConverter().fromString(price));
        grocery.setQuantity(Integer.parseInt(quantity));

        try {
            groceryHandler.create(grocery);
        } catch (DAOException e) {
            logger.error("cant groceryCreate",e);
            throw new GroceryServiceException("Невозможно сохранить новый продукт!",e);
        }
    }

    @Override
    public void groceryDelete(String groceryid) throws GroceryServiceException {
        Grocery grocery = null;
        List<ListGrocery> listGroceries = null;

        try {
            grocery = groceryHandler.getOne(UUID.fromString(groceryid));
            listGroceries = listGroceryHandler.getListByGroceryId(grocery.getId());
            groceryHandler.delete(grocery.getId());
            for(ListGrocery gl : listGroceries){
                listGroceryHandler.delete(gl.getId());
            }
        } catch (ListGroceryException e) {
            logger.error("cant getListByGeroceryId",e);
            throw new GroceryServiceException("Невозможно получить список соответствий продуктов!",e);
        } catch (DAOException e) {
            logger.error("cant groceryDelete");
            throw new GroceryServiceException("Невозможно удалить продукт!",e);
        }

    }

    @Override
    public void groceryUpdate(String groceryid, String name, String price, String quantity) throws GroceryServiceException, FormGroceryException {
        Grocery grocery = null;

        validator(name,price,quantity);

        try {
            grocery=groceryHandler.getOne(UUID.fromString(groceryid));

            if(grocery!=null){
                grocery.setName(name);
                grocery.setPrice(new BigDecimalStringConverter().fromString(price));
                grocery.setQuantity(Integer.parseInt(quantity));

                groceryHandler.update(grocery);
            }
            else {
                throw new FormGroceryException(new Message("Продукт не найден в базе!", Message.Status.ERROR));
            }
        } catch (DAOException e) {
            logger.error("cant groceryUpdate",e);
            throw new GroceryServiceException("Невозможно изменить информацию о продукте!",e);
        }
    }

    private void validator(String name, String price, String quantity) throws FormGroceryException {
        BigDecimal prc = null;
        Integer q = null;
        Message message = new Message();

        try {
            prc=new BigDecimalStringConverter().fromString(price);
            q = Integer.parseInt(quantity);
        } catch (NumberFormatException e){
            logger.error("cant parse parameters",e);
            message.addErrorMessage("Формат цены или количества неверен!");
        }

        if("".equals(name)) message.addErrorMessage("Наименование не должно быть пустым!");

        if(!message.isOk())throw new FormGroceryException(message);
    }

}
