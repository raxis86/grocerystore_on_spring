package Controllers.OrderControllers;

import Domain.Entities.User;
import Domain.Exceptions.DAOException;
import Services.Abstract.IListGroceryService;
import Services.Abstract.IOrderService;
import Services.Abstract.IUserService;
import Services.Concrete.ListGroceryService;
import Services.Concrete.OrderService;
import Services.Concrete.UserService;
import Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        IOrderService orderService = new OrderService();
        IListGroceryService listGroceryService = new ListGroceryService();
        IUserService userService = new UserService();

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
