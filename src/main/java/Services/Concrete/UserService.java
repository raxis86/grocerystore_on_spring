package Services.Concrete;

import Domain.Abstract.IRepositoryRole;
import Domain.Abstract.IRepositoryUser;
import Domain.Concrete.RoleSql;
import Domain.Concrete.UserSql;
import Domain.Entities.Role;
import Domain.Entities.User;
import Domain.Exceptions.DAOException;
import Domain.Exceptions.RoleException;
import Domain.Exceptions.UserException;
import Services.Abstract.IUserService;
import Services.Exceptions.FormUserException;
import Services.Models.Message;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 29.12.2016.
 */
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private IRepositoryUser userHandler;
    private IRepositoryRole roleHandler;

    public UserService(){
        this.userHandler = new UserSql();
        this.roleHandler = new RoleSql();
    }

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
