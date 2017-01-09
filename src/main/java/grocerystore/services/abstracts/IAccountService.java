package grocerystore.Services.Abstract;

import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Models.AuthUser;


/**
 * Created by raxis on 29.12.2016.
 */
public interface IAccountService {
    public AuthUser logIn(User user) throws DAOException;
    public AuthUser signIn(User user) throws DAOException;
}
