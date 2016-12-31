package Controllers.AccountControllers;

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
public class Logout extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Logout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session=req.getSession();
        session.invalidate();

        RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
        rd.forward(req,resp);
    }
}
