package grocerystore.domain.abstracts;

import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.UserException;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryUser extends IRepository<User,UUID> {
    public User getOne(String email, String passwordHash) throws UserException;
    public User getOneByEmail(String email) throws UserException;
}
