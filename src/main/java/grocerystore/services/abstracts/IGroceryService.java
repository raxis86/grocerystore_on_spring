package grocerystore.Services.Abstract;

import grocerystore.Domain.Entities.Grocery;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Models.Message;

import java.util.List;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IGroceryService {
    public List<Grocery> getGroceryList() throws DAOException;
    public Grocery getGrocery(String groceryid) throws DAOException;
    public void groceryCreate(String name, String price, String quantity) throws DAOException;
    public void groceryDelete(String groceryid) throws DAOException;
    public Message groceryUpdate(String groceryid, String name, String price, String quantity) throws DAOException;
}
