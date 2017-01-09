package grocerystore.filters;

import grocerystore.domain.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by raxis on 25.12.2016.
 * Фильтр для проверки, что пользователь с ролью администратор
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/GroceryListAdmin","/GroceryEdit","/GroceryDel","GroceryAdd"})
public class AdminFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        Role currentRole = null;
        if(session!=null){
            currentRole = (Role)session.getAttribute("role");
        }


        if(!currentRole.getName().equals("admin")){
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/deadend.jsp");
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
