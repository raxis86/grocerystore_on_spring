package grocerystore.Services.Abstract;

import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Domain.Exceptions.RoleException;
import grocerystore.Domain.Exceptions.UserException;
import grocerystore.Services.Exceptions.FormUserException;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IUserService {
    public User formUser(String email, String password, String name,
                         String lastname, String surname,
                         String address, String phone, String role) throws FormUserException, RoleException, UserException;
    public User formUserFromRepo(String email, String password) throws FormUserException, UserException;
    public void updateUser(User user, String name, String lastname,
                           String surname, String address, String phone) throws DAOException;
}
