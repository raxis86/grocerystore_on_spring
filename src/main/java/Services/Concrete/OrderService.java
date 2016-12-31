package Services.Concrete;

import Domain.Abstract.*;
import Domain.Concrete.*;
import Domain.Entities.*;
import Domain.Exceptions.DAOException;
import Services.Abstract.IOrderService;
import Services.Models.Cart;
import Services.ViewModels.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by raxis on 29.12.2016.
 */
public class OrderService implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private IRepositoryOrder orderHandler;
    private IRepositoryOrderStatus orderStatusHandler;
    private IRepositoryListGrocery listGroceryHandler;
    private IRepositoryGrocery groceryHandler;
    private IRepositoryUser userHandler;

    public OrderService(){
        this.orderHandler = new OrderSql();
        this.orderStatusHandler = new OrderStatusSql();
        this.listGroceryHandler = new ListGrocerySql();
        this.groceryHandler = new GrocerySql();
        this.userHandler = new UserSql();
    }

    @Override
    public Order createOrder(User user, Cart cart) throws DAOException {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserid(user.getId());
        order.setOrderstatusid(UUID.fromString("c24be575-187f-4d41-82ee-ff874764b829"));
        order.setPrice(cart.computeTotalPrice());
        order.setDatetime(new Date());
        order.setGrocerylistid(UUID.randomUUID());
        order.setAddress(user.getAddress());

        orderHandler.create(order);

        return order;
    }

    /**
     * формирование формы заказа для отображения
     * @param orderid
     * @return
     */
    @Override
    public OrderView formOrderView(String orderid) throws DAOException {
        return formOrderView(UUID.fromString(orderid),"");
    }

    @Override
    public List<OrderView> formOrderViewListAdmin() throws DAOException {
        List<OrderView> orderViewList = new ArrayList<>();
        List<Order> orderList=orderHandler.getAll();

        for(Order repoOrder : orderList){
            orderViewList.add(formOrderView(repoOrder.getId(),userHandler.getOne(repoOrder.getUserid()).getEmail()));
        }

        return orderViewList;
    }

    @Override
    public List<OrderView> formOrderViewList(User user) throws DAOException {
        List<OrderView> orderViewList = new ArrayList<>();
        List<Order> orderList=orderHandler.getByUserId(user.getId());

        for(Order repoOrder : orderList){
            if(!repoOrder.getOrderstatusid().toString().equals("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5")){
                orderViewList.add(formOrderView(repoOrder.getId(),String.format("%s %s %s",user.getLastName(),user.getName(),user.getSurName())));
            }
        }

        return orderViewList;
    }

    @Override
    public void updateOrder(String orderid) throws DAOException {
        Order order = orderHandler.getOne(UUID.fromString(orderid));

        order.setOrderstatusid(UUID.fromString("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"));

        orderHandler.update(order);
    }

    @Override
    public void updateOrderAdmin(String orderid, String statusid) throws DAOException {
        Order order = orderHandler.getOne(UUID.fromString(orderid));

        order.setOrderstatusid(UUID.fromString(statusid));

        orderHandler.update(order);
    }

    @Override
    public Order getOrder(String orderid) throws DAOException {
        return orderHandler.getOne(UUID.fromString(orderid));
    }

    private OrderView formOrderView(UUID orderid, String userName) throws DAOException {
        Map<String,Integer> map = new HashMap<>();
        Map<String,String> statusMap = new HashMap<>();

        Order order = orderHandler.getOne(orderid);
        List<ListGrocery> listGroceries = listGroceryHandler.getListById(order.getGrocerylistid());
        OrderStatus orderStatus = orderStatusHandler.getOne(order.getOrderstatusid());
        List<OrderStatus> orderStatusList = orderStatusHandler.getAll();

        OrderView orderView = new OrderView();

        orderView.setId(order.getId().toString());
        orderView.setAddress(order.getAddress());
        orderView.setStatus(orderStatus.getStatus());
        orderView.setDate(order.getDatetime().toString());
        orderView.setFullName(userName);
        orderView.setPrice(order.getPrice().toString());

        for(ListGrocery list: listGroceries){
            String str=groceryHandler.getOne(list.getGroceryId()).getName();
            map.put(str,Integer.valueOf(list.getQuantity()));
        }
        orderView.setGroceries(map);

        for(OrderStatus os : orderStatusList){
            statusMap.put(os.getId().toString(),os.getStatus());
        }

        orderView.setStatuses(statusMap);

        return orderView;
    }
}
