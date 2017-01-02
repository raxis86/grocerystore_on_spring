package OldControllers.OrderControllers;

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
import java.io.IOException;

/**
 * Created by raxis on 28.12.2016.
 */
public class OrderListAdmin extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(OrderListAdmin.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //IOrderService orderService = new OrderService();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});

        IOrderService orderService = (IOrderService) applicationContext.getBean("orderService");

        try {
            req.setAttribute("orderlist",orderService.formOrderViewListAdmin());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderlist_admin.jsp");
            rd.forward(req,resp);
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }
    }
}
