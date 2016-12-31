package Controllers.AccountControllers;

import Domain.Exceptions.DAOException;
import Domain.Exceptions.RoleException;
import Domain.Exceptions.UserException;
import Services.Abstract.IAccountService;
import Services.Abstract.IUserService;
import Services.Concrete.AccountService;
import Services.Concrete.UserService;
import Services.Exceptions.FormUserException;
import Services.Models.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by raxis on 27.12.2016.
 */
public class Signin extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Signin.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/signin.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        req.setAttribute("name",req.getParameter("name"));
        req.setAttribute("lastname",req.getParameter("lastname"));
        req.setAttribute("surname",req.getParameter("surname"));
        req.setAttribute("phone",req.getParameter("phone"));
        req.setAttribute("address",req.getParameter("address"));

        IAccountService accountService = new AccountService();
        IUserService userService = new UserService();

        AuthUser authUser=null;
        try {
            authUser = accountService.signIn(userService.formUser(req.getParameter("email"),
                                                                  req.getParameter("password"),
                                                                  req.getParameter("name"),
                                                                  req.getParameter("lastname"),
                                                                  req.getParameter("surname"),
                                                                  req.getParameter("address"),
                                                                  req.getParameter("phone"),
                                                                  "user"));
        } catch (FormUserException e) {
            req.setAttribute("messages",e.getExceptionMessage().getMessagesError());
            doGet(req,resp);
        } catch (RoleException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        } catch (UserException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        } catch (DAOException e) {
            req.setAttribute("message",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("WEB-INF/exception.jsp");
            rd.forward(req,resp);
        }

        if(authUser!=null){
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/signinsuccess.jsp");
            rd.forward(req,resp);
        }
    }
}
