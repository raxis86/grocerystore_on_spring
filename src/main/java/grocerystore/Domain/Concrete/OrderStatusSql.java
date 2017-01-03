package grocerystore.Domain.Concrete;

import grocerystore.Domain.Abstract.IRepositoryOrderStatus;
import grocerystore.Domain.Entities.OrderStatus;
import grocerystore.Domain.Exceptions.OrderStatusException;
import grocerystore.Tools.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static grocerystore.Constants.Constants.*;

/**
 * Created by raxis on 27.12.2016.
 * Реализакция DAO для работы с orderstatus в MySQL
 */
@Component
public class OrderStatusSql extends SQLImplementation implements IRepositoryOrderStatus {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusSql.class);

    @Override
    public List<OrderStatus> getAll() throws OrderStatusException {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        try(Connection connection = getDs().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(ORDERSTATUS_SELECTALL_QUERY);) {
            while (resultSet.next()){
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(UUID.fromString(resultSet.getString("ID")));
                orderStatus.setStatus(resultSet.getString("STATUS"));
                orderStatusList.add(orderStatus);
            }
        } catch (SQLException e) {
            logger.error("cant gelAll",e);
            throw new OrderStatusException("Проблема с базой данных: невозможно получить записи из таблицы статусов!");
        }
        return orderStatusList;
    }

    @Override
    public OrderStatus getOne(UUID id) throws OrderStatusException {
        OrderStatus orderStatus = null;
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ORDERSTATUS_PREP_SELECTONE_QUERY)) {
            statement.setObject(1,id.toString());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                orderStatus = new OrderStatus();
                orderStatus.setId(UUID.fromString(resultSet.getString("ID")));
                orderStatus.setStatus(resultSet.getString("STATUS"));
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Cant getOne OrderStatusSql!", e);
            throw new OrderStatusException("Проблема с базой данных: невозможно получить запись из таблицы статусов!");
        }
        return orderStatus;
    }

    @Override
    public boolean create(OrderStatus entity) throws OrderStatusException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ORDERSTATUS_PREP_INSERT_QUERY);) {
            statement.setObject(1,entity.getId().toString());
            statement.setObject(2,entity.getStatus());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant create",e);
            throw new OrderStatusException("Проблема с базой данных: невозможно создать запись в таблице статусов!");
        }
        return true;
    }

    @Override
    public boolean delete(UUID id) throws OrderStatusException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement = connection.prepareStatement(ORDERSTATUS_PREP_DELETE_QUERY);) {
            statement.setObject(1,id.toString());
            statement.execute();
        } catch (SQLException e) {
            logger.error("cant delete",e);
            throw new OrderStatusException("Проблема с базой данных: невозможно удалить запись в таблице статусов!");
        }
        return true;
    }

    @Override
    public boolean update(OrderStatus entity) throws OrderStatusException {
        try(Connection connection = getDs().getConnection();
            PreparedStatement statement=connection.prepareStatement(ORDERSTATUS_PREP_UPDATE_QUERY);) {
            statement.setObject(1,entity.getStatus());
            statement.setObject(2,entity.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cant update",e);
            throw new OrderStatusException("Проблема с базой данных: невозможно изменить запись в таблице статусов!");
        }
        return true;
    }
}
