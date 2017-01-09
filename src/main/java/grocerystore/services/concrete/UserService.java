package grocerystore.Services.Concrete;

import grocerystore.Domain.Abstract.IRepositoryRole;
import grocerystore.Domain.Abstract.IRepositoryUser;
import grocerystore.Domain.Concrete.RoleSql;
import grocerystore.Domain.Concrete.UserSql;
import grocerystore.Domain.Entities.Role;
import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Domain.Exceptions.RoleException;
import grocerystore.Domain.Exceptions.UserException;
import grocerystore.Services.Abstract.IUserService;
import grocerystore.Services.Exceptions.FormUserException;
import grocerystore.Services.Models.Message;
import grocerystore.Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
@Component
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IRepositoryUser userHandler;
    @Autowired
    private IRepositoryRole roleHandler;

    public UserService(IRepositoryUser userHandler,
                       IRepositoryRole roleHandler){
        this.userHandler=userHandler;
        this.roleHandler=roleHandler;
    }

    /*public UserService(){
        this.userHandler = new UserSql();
        this.roleHandler = new RoleSql();
    }*/

    @Override
    public User formUser(String email, String password, String name,
                         String lastname, String surname, String address,
                         String phone, String roleName) throws FormUserException, RoleException, UserException {

        Message message = new Message();
        User user = new User();
        Role roleByName = roleHandler.roleByRoleName(roleName);

        if(email.toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
            if(userHandler.getOneByEmail(email)!=null){
                message.addErrorMessage("Пользователь с таким email уже существует в базе!");
                throw new FormUserException(message);
            }
        }
        else {
            message.addErrorMessage("email некорректен!");
        }

        if(password.length()<6){
            message.addErrorMessage("длина пароля должна быть не менее 6 символов");
        }

        if(roleByName==null){
            message.addErrorMessage("Роли с таким наименованием не существует!");
            throw new FormUserException(message);
        }

        if(message.isOk()){
            user.setId(UUID.randomUUID());
            user.setRoleID(UUID.fromString("81446dc5-bd04-4d41-bd72-7405effb4716"));
            user.setEmail(email.toLowerCase());
            user.setSalt(Tool.generateSalt());
            user.setPassword(Tool.computeHash(Tool.computeHash(password) + user.getSalt()));
            user.setName(name);
            user.setLastName(lastname);
            user.setSurName(surname);
            user.setPhone(phone);
            user.setAddress(address);
        }
        else {
            throw new FormUserException(message);
        }

        return user;
    }

    @Override
    public User formUserFromRepo(String email, String password) throws FormUserException, UserException {
        //Ищем, что существует юзер с таким email
        Message message = new Message();

        User userByEmail = userHandler.getOneByEmail(email);
        if(userByEmail==null){
            message.addErrorMessage("Пользователь с таким email не найден!");
            throw new FormUserException(message);
        }

        User user = userHandler.getOne(email.toLowerCase(), Tool.computeHash(Tool.computeHash(password) + userByEmail.getSalt()));

        if(user==null){
            message.addErrorMessage("Неверный пароль!");
            throw new FormUserException(message);
        }

        return user;
    }

    @Override
    public void updateUser(User user, String name, String lastname,
                           String surname, String address, String phone) throws DAOException {
        user.setName(name);
        user.setLastName(lastname);
        user.setSurName(surname);
        user.setAddress(address);
        user.setPhone(phone);

        userHandler.update(user);
    }
}
