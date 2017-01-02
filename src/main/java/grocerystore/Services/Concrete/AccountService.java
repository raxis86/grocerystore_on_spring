package grocerystore.Services.Concrete;

import grocerystore.Domain.Abstract.IRepositoryRole;
import grocerystore.Domain.Abstract.IRepositoryUser;
import grocerystore.Domain.Concrete.RoleSql;
import grocerystore.Domain.Concrete.UserSql;
import grocerystore.Domain.Entities.Role;
import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IAccountService;
import grocerystore.Services.Models.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by raxis on 29.12.2016.
 * Сервис для регистрации и аутентификации пользователя
 */
@Component
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private IRepositoryUser userHandler;
    @Autowired
    private IRepositoryRole roleHandler;

    public AccountService(IRepositoryUser userHandler, IRepositoryRole roleHandler){
        this.userHandler=userHandler;
        this.roleHandler=roleHandler;
    }

    /*public AccountService(){
        this.userHandler=new UserSql();
        this.roleHandler=new RoleSql();
    }*/

    /**
     * Метод для аутентификации пользователя
     * @param user
     * @return
     */
    @Override
    public AuthUser logIn(User user) throws DAOException {
        Role role = roleHandler.getOne(user.getRoleID());
        return new AuthUser(user,role);
    }

    /**
     * Метод для регистрации пользователя
     * @param user
     * @return
     * @throws DAOException
     */
    @Override
    public AuthUser signIn(User user) throws DAOException {
        userHandler.create(user);
        Role role = roleHandler.getOne(user.getRoleID());
        return new AuthUser(user,role);
    }

}
