package Controllers.CartController;

import Domain.Exceptions.DAOException;
import Services.Abstract.ICartService;
import Services.Concrete.CartService;
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
 * Created by raxis on 25.12.2016.
 */
public class CartAdd extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartAdd.class);

    /**
     * В этом запросе товар добавляется в корзину покупок
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        ICartService cartService = new CartService();

        HttpSession session = req.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if(cart==null){
            cart=new Cart();
            session.setAttribute("cart",cart);
        }
        try {
            cartService.addToCart(cart,req.getParameter("groceryid"));
            RequestDispatcher rd=req.getRequestDispatcher("/GroceryListController");
            rd.forward(req,resp);
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

    }

}
