package grocerystore.services.abstracts;

import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.exceptions.AccountServiceException;
import grocerystore.services.models.AuthUser;


/**
 * Created by raxis on 29.12.2016.
 */
public interface IAccountService {
    public AuthUser logIn(User user) throws AccountServiceException;
    public AuthUser signIn(User user) throws AccountServiceException;
}
