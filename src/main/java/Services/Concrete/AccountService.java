package Services.Concrete;

import Domain.Abstract.IRepositoryRole;
import Domain.Abstract.IRepositoryUser;
import Domain.Concrete.RoleSql;
import Domain.Concrete.UserSql;
import Domain.Entities.Role;
import Domain.Entities.User;
import Domain.Exceptions.DAOException;
import Services.Abstract.IAccountService;
import Services.Models.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 29.12.2016.
 * Сервис для регистрации и аутентификации пользователя
 */
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;

    public AccountService(){
        this.userHandler=new UserSql();
        this.roleHandler=new RoleSql();
    }

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
