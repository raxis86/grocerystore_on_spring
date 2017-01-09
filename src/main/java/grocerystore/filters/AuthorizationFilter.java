package grocerystore.filters;

import grocerystore.domain.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by raxis on 25.12.2016.
 * Фильтр для проверки, что пользователь авторизован
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/Cart*","/Order*"})
public class AuthorizationFilter implements Filter{
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Get init parameter
        String testParam = filterConfig.getInitParameter("test-param");
        //Print the init parameter
        System.out.println("Test Param: " + testParam);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        User currentUser = null;

        if(session!=null){
            currentUser = (User)session.getAttribute("user");
        }


        if(currentUser==null){
            RequestDispatcher rd = request.getRequestDispatcher("/Login");
            rd.forward(request,response);
        }
        else {
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
