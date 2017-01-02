package OldControllers.CartController;

import grocerystore.Services.Models.Cart;
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
 * Created by raxis on 25.12.2016.
 */
public class CartList extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartList.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");

        if(cart!=null){
            req.setAttribute("cart",cart);
            req.setAttribute("totalprice",cart.computeTotalPrice().toString());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/cart.jsp");
            rd.forward(req,resp);
        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req,resp);
        }

    }
}
