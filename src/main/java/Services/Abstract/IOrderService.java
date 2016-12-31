package Services.Abstract;

import Domain.Entities.Order;
import Domain.Entities.User;
import Domain.Exceptions.DAOException;
import Services.Models.Cart;
import Services.ViewModels.OrderView;

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
