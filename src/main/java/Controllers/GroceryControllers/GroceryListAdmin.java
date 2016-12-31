package Controllers.GroceryControllers;

import Domain.Exceptions.DAOException;
import Services.Abstract.IGroceryService;
import Services.Concrete.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by raxis on 25.12.2016.
 */
public class GroceryListAdmin extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GroceryListAdmin.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        IGroceryService groceryService = new GroceryService();

        try {
            req.setAttribute("groceryList", groceryService.getGroceryList());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/grocerylist_admin.jsp");
            rd.forward(req,resp);
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
