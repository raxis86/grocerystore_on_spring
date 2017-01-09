package grocerystore.services.abstracts;

import grocerystore.domain.entities.Order;
import grocerystore.domain.entities.User;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.exceptions.OrderServiceException;
import grocerystore.services.models.Cart;
import grocerystore.services.viewmodels.OrderView;

import java.util.List;

/**
 * Created by raxis on 29.12.2016.
 */
public interface IOrderService {
    public Order createOrder(User user, Cart cart) throws OrderServiceException;
    public OrderView formOrderView(String orderid) throws OrderServiceException;
    public List<OrderView> formOrderViewListAdmin() throws OrderServiceException;
    public List<OrderView> formOrderViewList(User user) throws OrderServiceException;
    public void updateOrder(String orderid) throws OrderServiceException;
    public void updateOrderAdmin(String orderid, String statusid) throws OrderServiceException;
    public Order getOrder(String orderid) throws OrderServiceException;
}
