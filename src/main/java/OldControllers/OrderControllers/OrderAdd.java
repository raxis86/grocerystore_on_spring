package OldControllers.OrderControllers;

import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IListGroceryService;
import grocerystore.Services.Abstract.IOrderService;
import grocerystore.Services.Abstract.IUserService;
import grocerystore.Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by raxis on 26.12.2016.
 */
public class OrderAdd extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(OrderAdd.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("user");

        if((cart!=null)&&(user!=null)){
            req.setAttribute("user",user);
            req.setAttribute("cart",cart);
            req.setAttribute("totalprice",cart.computeTotalPrice().toString());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderadd.jsp");
            rd.forward(req,resp);
        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        /*IOrderService orderService = new OrderService();
        IListGroceryService listGroceryService = new ListGroceryService();
        IUserService userService = new UserService();*/

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});

        IOrderService orderService = (IOrderService) applicationContext.getBean("orderService");
        IListGroceryService listGroceryService = (IListGroceryService) applicationContext.getBean("listGroceryService");
        IUserService userService = (IUserService) applicationContext.getBean("userService");

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("user");

        if((cart!=null)&&(user!=null)){
            try {
                userService.updateUser(user,
                        req.getParameter("name"),
                        req.getParameter("lastname"),
                        req.getParameter("surname"),
                        req.getParameter("address"),
                        req.getParameter("phone"));

                listGroceryService.createListGrocery(cart,orderService.createOrder(user,cart));

                cart.clear();

                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/ordersuccess.jsp");
                rd.forward(req,resp);
            } catch (DAOException e) {
                req.setAttribute("message",e.getMessage());
                RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
                rd.forward(req,resp);
            }
        }

    }
}
