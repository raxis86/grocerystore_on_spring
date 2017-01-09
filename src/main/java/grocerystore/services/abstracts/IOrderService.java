package grocerystore.Services.Abstract;

import grocerystore.Domain.Entities.Order;
import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Models.Cart;
import grocerystore.Services.ViewModels.OrderView;

import java.util.List;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IOrderService {
    public Order createOrder(User user, Cart cart) throws DAOException;
    public OrderView formOrderView(String orderid) throws DAOException;
    public List<OrderView> formOrderViewListAdmin() throws DAOException;
    public List<OrderView> formOrderViewList(User user) throws DAOException;
    public void updateOrder(String orderid) throws DAOException;
    public void updateOrderAdmin(String orderid, String statusid) throws DAOException;
    public Order getOrder(String orderid) throws DAOException;
}
