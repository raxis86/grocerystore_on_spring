package grocerystore.services.concrete;

import grocerystore.domain.abstracts.IRepositoryRole;
import grocerystore.domain.abstracts.IRepositoryUser;
import grocerystore.domain.entities.Role;
import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.abstracts.IAccountService;
import grocerystore.services.exceptions.AccountServiceException;
import grocerystore.services.models.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by raxis on 29.12.2016.
 * Сервис для регистрации и аутентификации пользователя
 */
@Service
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;

    public AccountService(IRepositoryUser userHandler, IRepositoryRole roleHandler){
        this.userHandler=userHandler;
        this.roleHandler=roleHandler;
    }

    /**
     * Метод для аутентификации пользователя
     * @param user
     * @return
     */
    @Override
    public AuthUser logIn(User user) throws AccountServiceException {
        Role role = null;
        try {
            role = roleHandler.getOne(user.getRoleID());
        } catch (DAOException e) {
            logger.error("cant logIn",e);
            throw new AccountServiceException("Невозможно осуществить вход в систему!",e);
        }
        return new AuthUser(user,role);
    }

    /**
     * Метод для регистрации пользователя
     * @param user
     * @return
     * @throws DAOException
     */
    @Override
    public AuthUser signIn(User user) throws AccountServiceException {
        Role role = null;
        try {
            userHandler.create(user);
            role = roleHandler.getOne(user.getRoleID());
        } catch (DAOException e) {
            logger.error("cant signIn!",e);
            throw new AccountServiceException("Невозможно зарегистрировать пользователя!",e);
        }

        return new AuthUser(user,role);
    }

}
