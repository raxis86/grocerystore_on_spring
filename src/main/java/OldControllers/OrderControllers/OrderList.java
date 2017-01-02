package OldControllers.OrderControllers;

import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IOrderService;
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
public class OrderList extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderList.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //IOrderService orderService = new OrderService();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});

        IOrderService orderService = (IOrderService)applicationContext.getBean("orderService");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user!=null){

            try {
                req.setAttribute("orderlist",orderService.formOrderViewList(user));
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderlist.jsp");
                rd.forward(req,resp);
            } catch (DAOException e) {
                req.setAttribute("message",e.getMessage());
                RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
                rd.forward(req,resp);
            }

        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/deadend.jsp");
            rd.forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //IOrderService orderService = new OrderService();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});

        IOrderService orderService = (IOrderService) applicationContext.getBean("orderService");

        try {
            orderService.updateOrder(req.getParameter("orderid"));
            doGet(req,resp);
            /*RequestDispatcher rd = req.getRequestDispatcher("/GroceryListController");
            rd.forward(req,resp);*/
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }
    }
}
