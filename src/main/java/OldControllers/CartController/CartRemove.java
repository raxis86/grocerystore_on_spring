package OldControllers.CartController;

import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.ICartService;
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
 * Created by raxis on 25.12.2016.
 */
public class CartRemove extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartRemove.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //ICartService cartService = new CartService();

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});
        ICartService cartService = (ICartService) applicationContext.getBean("cartService");

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");

        try {
            cartService.removeFromCart(cart, req.getParameter("groceryid"));
            RequestDispatcher rd=req.getRequestDispatcher("/CartList");
            rd.forward(req,resp);
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }
    }
}
