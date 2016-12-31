package Domain.Concrete;

import Domain.Abstract.IRepositoryRole;
import Domain.Entities.Role;
import Domain.Exceptions.RoleException;
import Tools.DatabaseManager;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Constants.Constants.*;

/**
 * Created by raxis on 27.12.2016.
 * Реализакция DAO для работы с role в MySQL
 */
public class RoleSql implements IRepositoryRole {
    private static final Logger logger = LoggerFactory.getLogger(RoleSql.class);

    @Override
    public List<Role> getAll() throws RoleException {
        List<Role> roleList = new ArrayList<>();
        try(Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(ROLE_SELECTALL_QUERY);) {
            while (resultSet.next()){
                Role role = new Role();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setName(resultSet.getString("NAME"));
                roleList.add(role);
            }
        } catch (SQLException e) {
            logger.error("cant getAll",e);
            throw new RoleException("Проблема с базой данных: невозможно получить записи из таблицы ролей!");
        }
        return roleList;
    }

    @Override
    public Role getOne(UUID id) throws RoleException {
        Role role = null;
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLE_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                role = new Role();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setName(resultSet.getString("NAME"));

            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne Role!", e);
            throw new RoleException("Проблема с базой данных: невозможно получить запись из таблицы ролей!");
        }
        return role;
    }

    @Override
    public boolean create(Role entity) throws RoleException {
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLE_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getName());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant create",e);
            throw new RoleException("Проблема с базой данных: невозможно создать запись в таблице ролей!");
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) throws RoleException {
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLE_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant delete",e);
            throw new RoleException("Проблема с базой данных: невозможно удалить запись из таблицы ролей!");
        }
        return true;
    }

    @Override
    public boolean update(Role entity) throws RoleException {
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement=connection.prepareStatement(ROLE_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getName());
            statement.setObject(2,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cant update",e);
            throw new RoleException("Проблема с базой данных: невозможно изменить запись в таблице ролей!");
        }
        return true;
    }

    @Override
    public Role roleByRoleName(String roleName) throws RoleException {
        Role role = null;
        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(ROLE_PREP_SELECTONE_BY_NAME_QUERY)) {
            statement.setObject(1,roleName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                role = new Role();
                role.setId(UUID.fromString(resultSet.getString("ID")));
                role.setName(resultSet.getString("NAME"));

            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne Role!", e);
            throw new RoleException("Проблема с базой данных: невозможно получить запись из таблицы ролей!");
        }
        return role;
    }
}
