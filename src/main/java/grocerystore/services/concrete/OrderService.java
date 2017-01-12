package grocerystore.services.concrete;

import grocerystore.domain.abstracts.*;
import grocerystore.domain.entities.*;
import grocerystore.domain.exceptions.DAOException;
import grocerystore.domain.exceptions.ListGroceryException;
import grocerystore.domain.exceptions.OrderException;
import grocerystore.services.abstracts.IOrderService;
import grocerystore.services.exceptions.OrderServiceException;
import grocerystore.services.models.Cart;
import grocerystore.services.viewmodels.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by raxis on 29.12.2016.
 */
@Service
public class OrderService implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private IRepositoryOrder orderHandler;
    private IRepositoryOrderStatus orderStatusHandler;
    private IRepositoryListGrocery listGroceryHandler;
    private IRepositoryGrocery groceryHandler;
    private IRepositoryUser userHandler;

    public OrderService(IRepositoryOrder orderHandler,
                        IRepositoryOrderStatus orderStatusHandler,
                        IRepositoryListGrocery listGroceryHandler,
                        IRepositoryGrocery groceryHandler,
                        IRepositoryUser userHandler){
        this.orderHandler=orderHandler;
        this.orderStatusHandler=orderStatusHandler;
        this.listGroceryHandler=listGroceryHandler;
        this.groceryHandler=groceryHandler;
        this.userHandler=userHandler;
    }

    @Override
    public Order createOrder(User user, Cart cart) throws OrderServiceException {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserid(user.getId());
        order.setOrderstatusid(UUID.fromString("c24be575-187f-4d41-82ee-ff874764b829"));
        order.setPrice(cart.computeTotalPrice());
        order.setDatetime(new Date());
        order.setGrocerylistid(UUID.randomUUID());
        order.setAddress(user.getAddress());

        try {
            orderHandler.create(order);
        } catch (DAOException e) {
            logger.error("cant createOrder",e);
            throw new OrderServiceException("Невозможно сформировать заказ!",e);
        }

        return order;
    }

    /**
     * формирование формы заказа для отображения
     * @param orderid
     * @return
     */
    @Override
    public OrderView formOrderView(String orderid) throws OrderServiceException {
        return formOrderView(UUID.fromString(orderid),"");
    }

    @Override
    public List<OrderView> formOrderViewListAdmin() throws OrderServiceException {
        List<OrderView> orderViewList = new ArrayList<>();
        List<Order> orderList=null;

        try {
            orderList=orderHandler.getAll();
        } catch (DAOException e) {
            logger.error("cant getAll",e);
            throw new OrderServiceException("Невозможно сформировать список заказов!",e);
        }

        for(Order repoOrder : orderList){
            try {
                orderViewList.add(formOrderView(repoOrder.getId(),userHandler.getOne(repoOrder.getUserid()).getEmail()));
            } catch (DAOException e) {
                logger.error("cant user.getOne",e);
                throw new OrderServiceException("Невозможно сформировать список заказов!",e);
            }
        }

        return orderViewList;
    }

    @Override
    public List<OrderView> formOrderViewList(User user) throws OrderServiceException {
        List<OrderView> orderViewList = new ArrayList<>();
        List<Order> orderList=null;

        try {
            orderList=orderHandler.getByUserId(user.getId());
        } catch (OrderException e) {
            logger.error("cant order.getByUserId",e);
            throw new OrderServiceException("Невозможно сформировать список заказов!",e);
        }

        for(Order repoOrder : orderList){
            if(!repoOrder.getOrderstatusid().toString().equals("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5")){
                orderViewList.add(formOrderView(repoOrder.getId(),String.format("%s %s %s",user.getLastName(),user.getName(),user.getSurName())));
            }
        }

        return orderViewList;
    }

    @Override
    public void updateOrder(String orderid) throws OrderServiceException {
        Order order = null;

        try {
            order=orderHandler.getOne(UUID.fromString(orderid));
        } catch (DAOException e) {
            logger.error("cant order.getOne",e);
            throw new OrderServiceException("Невозможно сохранить изменения!",e);
        }

        order.setOrderstatusid(UUID.fromString("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"));

        try {
            orderHandler.update(order);
        } catch (DAOException e) {
            logger.error("cant update order",e);
            throw new OrderServiceException("Невозможно сохранить изменения!",e);
        }
    }

    @Override
    public void updateOrderAdmin(String orderid, String statusid) throws OrderServiceException {
        Order order = null;

        try {
            order=orderHandler.getOne(UUID.fromString(orderid));
        } catch (DAOException e) {
            logger.error("cant order.getOne",e);
            throw new OrderServiceException("Невозможно сохранить изменения!",e);
        }

        order.setOrderstatusid(UUID.fromString(statusid));

        try {
            orderHandler.update(order);
        } catch (DAOException e) {
            logger.error("cant update order",e);
            throw new OrderServiceException("Невозможно сохранить изменения!",e);
        }
    }

    @Override
    public Order getOrder(String orderid) throws OrderServiceException {
        Order order=null;

        try {
            order=orderHandler.getOne(UUID.fromString(orderid));
        } catch (DAOException e) {
            logger.error("cant order.getOne",e);
            throw new OrderServiceException("Невозможно найти заказ!",e);
        }

        return order;
    }

    private OrderView formOrderView(UUID orderid, String userName) throws OrderServiceException {
        Map<String,Integer> map = new HashMap<>();
        Map<String,String> statusMap = new HashMap<>();
        Order order = null;
        List<ListGrocery> listGroceries = null;
        OrderStatus orderStatus = null;
        List<OrderStatus> orderStatusList = null;

        try {
            order = orderHandler.getOne(orderid);
            listGroceries = listGroceryHandler.getListById(order.getGrocerylistid());
            orderStatus = orderStatusHandler.getOne(order.getOrderstatusid());
            orderStatusList = orderStatusHandler.getAll();
        } catch (ListGroceryException e) {
            logger.error("cant ListGrocery.getListById",e);
            throw new OrderServiceException("Невозможно сформировать заказ!",e);
        } catch (DAOException e) {
            logger.error("cant order get",e);
            throw new OrderServiceException("Невозможно сформировать заказ!",e);
        }


        OrderView orderView = new OrderView();

        orderView.setId(order.getId().toString());
        orderView.setAddress(order.getAddress());
        orderView.setStatus(orderStatus.getStatus());
        orderView.setDate(order.getDatetime().toString());
        orderView.setFullName(userName);
        orderView.setPrice(order.getPrice().toString());

        for(ListGrocery list: listGroceries){
            String str= null;
            try {
                str = groceryHandler.getOne(list.getGroceryId()).getName();
            } catch (DAOException e) {
                logger.error("cant grocery.getOne",e);
                throw new OrderServiceException("Невозможно сформировать заказ!",e);
            }
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
