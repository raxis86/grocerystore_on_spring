package Services.Abstract;

import Domain.Entities.User;
import Domain.Exceptions.DAOException;
import Services.Models.AuthUser;


/**
 * Created by raxis on 29.12.2016.
 */
public interface IAccountService {
    public AuthUser logIn(User user) throws DAOException;
    public AuthUser signIn(User user) throws DAOException;
}
