package Controllers.OrderControllers;

import Domain.Exceptions.DAOException;
import Services.Abstract.IOrderService;
import Services.Concrete.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by raxis on 28.12.2016.
 */
public class OrderEdit extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderEdit.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        IOrderService orderService = new OrderService();

        try {
            req.setAttribute("order",orderService.formOrderView(req.getParameter("orderid")));
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderedit.jsp");
            rd.forward(req,resp);
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        IOrderService orderService = new OrderService();

        try {
            orderService.updateOrderAdmin(req.getParameter("orderid"),req.getParameter("statusid"));

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
