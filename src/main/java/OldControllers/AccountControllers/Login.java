package OldControllers.AccountControllers;

import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Domain.Exceptions.UserException;
import grocerystore.Services.Abstract.IAccountService;
import grocerystore.Services.Abstract.IUserService;
import grocerystore.Services.Exceptions.FormUserException;
import grocerystore.Services.Models.AuthUser;
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
public class Login extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/login.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});

        IAccountService accountService = (IAccountService) applicationContext.getBean("accountService");
        IUserService userService = (IUserService) applicationContext.getBean("userService");

        //IAccountService accountService = new AccountService();
        //IUserService userService = new UserService();

        AuthUser authUser=null;

        try {
            authUser=accountService.logIn(userService.formUserFromRepo(req.getParameter("email"),
                                                                       req.getParameter("password")));
        } catch (FormUserException e) {
            req.setAttribute("messages",e.getExceptionMessage().getMessagesError());
            doGet(req,resp);
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
            HttpSession session = req.getSession(true);
            session.setAttribute("user",authUser.getUser());
            session.setAttribute("role",authUser.getRole());
            RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");
            rd.forward(req,resp);
        }

    }
}
