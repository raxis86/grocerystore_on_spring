package grocerystore.domain.concrete;

import grocerystore.domain.abstracts.IRepositoryUser;
import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static grocerystore.constants.Constants.*;

/**
 * Created by raxis on 27.12.2016.
 * Реализакция DAO для работы с user в MySQL
 */
@Component
public class UserSql extends SQLImplementation implements IRepositoryUser {
    private static final Logger logger = LoggerFactory.getLogger(UserSql.class);


    private void fillUser(User usr, ResultSet resultSet) throws SQLException {
        usr.setId(UUID.fromString(resultSet.getString("ID")));
        usr.setRoleID(UUID.fromString(resultSet.getString("ROLEID")));
        usr.setName(resultSet.getString("NAME"));
        usr.setEmail(resultSet.getString("EMAIL"));
        usr.setPassword(resultSet.getString("PASSWORD"));
        usr.setSalt(resultSet.getString("SALT"));
        usr.setLastName(resultSet.getString("LASTNAME"));
        usr.setSurName(resultSet.getString("SURNAME"));
        usr.setAddress(resultSet.getString("ADDRESS"));
        usr.setPhone(resultSet.getString("PHONE"));
    }

    @Override
    public List<User> getAll() throws UserException {
        List<User> userList = new ArrayList<>();
        try(Connection connection = getDs().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(USER_SELECTALL_QUERY);) {
            while (resultSet.next()){
                User usr = new User();
                fillUser(usr,resultSet);
                userList.add(usr);
            }
        } catch (SQLException e) {
            logger.error("Select Users error!",e);
            throw new UserException("Проблема с базой данных: невозможно получить записи из таблицы пользователей!",e);
        }
        return userList;
    }

    @Override
    public User getOne(UUID id) throws UserException {
        User usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_PREP_SELECTONE_QUERY);) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new User();
                fillUser(usr,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne User!",e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return usr;
    }

    @Override
    public boolean create(User entity) throws UserException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getRoleID().toString());
            statement.setObject(3,entity.getName());
            statement.setObject(4,entity.getEmail());
            statement.setObject(5,entity.getPassword());
            statement.setObject(6,entity.getSalt());
            statement.setObject(7,entity.getLastName());
            statement.setObject(8,entity.getSurName());
            statement.setObject(9,entity.getAddress());
            statement.setObject(10,entity.getPhone());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Insert user error!", e);
            throw new UserException("Проблема с базой данных: невозможно создать запись в таблице пользователей!",e);
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) throws UserException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Cant delete User!",e);
            throw new UserException("Проблема с базой данных: невозможно удалить запись из таблицы пользователей!",e);
        }
        return true;
    }

    @Override
    public boolean update(User entity) throws UserException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement=connection.prepareStatement(USER_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getRoleID().toString());
            statement.setObject(2,entity.getName());
            statement.setObject(3,entity.getEmail());
            statement.setObject(4,entity.getPassword());
            statement.setObject(5,entity.getSalt());
            statement.setObject(6,entity.getLastName());
            statement.setObject(7,entity.getSurName());
            statement.setObject(8,entity.getAddress());
            statement.setObject(9,entity.getPhone());
            statement.setObject(10,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cant update User!",e);
            throw new UserException("Проблема с базой данных: невозможно изменить запись в таблице пользователей!",e);
        }
        return true;
    }

    @Override
    public User getOne(String email, String passwordHash) throws UserException {
        User usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_PREP_SELECTONE_BY_AUTH_QUERY)) {
            statement.setObject(1,email);
            statement.setObject(2,passwordHash);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new User();
                fillUser(usr,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne User by auth info",e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return usr;
    }

    @Override
    public User getOneByEmail(String email) throws UserException {
        User usr = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(USER_PREP_SELECTONE_BY_EMAIL_QUERY)) {
            statement.setObject(1,email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                usr = new User();
                fillUser(usr,resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne User by auth info",e);
            throw new UserException("Проблема с базой данных: невозможно получить запись из таблицы пользователей!",e);
        }
        return usr;
    }
}
