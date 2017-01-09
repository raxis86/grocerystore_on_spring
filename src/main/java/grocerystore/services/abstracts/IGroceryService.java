package grocerystore.services.abstracts;

import grocerystore.domain.entities.Grocery;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.exceptions.FormGroceryException;
import grocerystore.services.exceptions.GroceryServiceException;
import grocerystore.services.models.Message;

import java.util.List;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IGroceryService {
    public List<Grocery> getGroceryList() throws GroceryServiceException;
    public Grocery getGrocery(String groceryid) throws GroceryServiceException;
    public void groceryCreate(String name, String price, String quantity) throws GroceryServiceException, FormGroceryException;
    public void groceryDelete(String groceryid) throws GroceryServiceException;
    public void groceryUpdate(String groceryid, String name, String price, String quantity) throws GroceryServiceException, FormGroceryException;
}
