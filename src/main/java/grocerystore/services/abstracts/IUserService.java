package grocerystore.services.abstracts;

import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.domain.exceptions.RoleException;
import grocerystore.domain.exceptions.UserException;
import grocerystore.services.exceptions.FormUserException;
import grocerystore.services.exceptions.UserServiceException;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IUserService {
    public User formUser(String email, String password, String name,
                         String lastname, String surname,
                         String address, String phone, String role) throws UserServiceException, FormUserException;
    public User formUserFromRepo(String email, String password) throws UserServiceException, FormUserException;
    public void updateUser(User user, String name, String lastname,
                           String surname, String address, String phone) throws UserServiceException;
}
