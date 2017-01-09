package grocerystore.Controllers;

import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Domain.Exceptions.RoleException;
import grocerystore.Domain.Exceptions.UserException;
import grocerystore.Services.Abstract.IAccountService;
import grocerystore.Services.Abstract.IUserService;
import grocerystore.Services.Exceptions.FormUserException;
import grocerystore.Services.Models.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 * Created by raxis on 31.12.2016.
 */
@Controller
@SessionAttributes(value = {"user","role"})
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private IAccountService accountService;
    private IUserService userService;

    public AccountController(IAccountService accountService,IUserService userService){
        this.accountService=accountService;
        this.userService=userService;
    }

    //@GetMapping("/Login")
    @RequestMapping(value = "Login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    //@PostMapping("/Login")
    @RequestMapping(value = "Login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model){
        AuthUser authUser=null;
        try {
            authUser=accountService.logIn(userService.formUserFromRepo(email,password));
        } catch (FormUserException e) {
            model.addAttribute("messages",e.getExceptionMessage().getMessagesError());
            return "login";
        } catch (UserException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }

        if(authUser!=null){
            model.addAttribute("user",authUser.getUser());
            model.addAttribute("role",authUser.getRole());
            return "index";
        }

        return "login";
    }

    @RequestMapping(value = "Logout", method = RequestMethod.GET)
    public String logout(SessionStatus status){
        status.setComplete();
        return "index";
    }

    @RequestMapping(value = "Signin", method = RequestMethod.GET)
    public String signin(){
        return "signin";
    }

    @RequestMapping(value = "Signin", method = RequestMethod.POST)
    public String signin(@RequestParam("email") String email, @RequestParam("password") String password,
                         @RequestParam("name") String name, @RequestParam("lastname") String lastname,
                         @RequestParam("surname") String surname, @RequestParam("phone") String phone,
                         @RequestParam("address") String address, Model model){

        AuthUser authUser=null;
        try {
            authUser = accountService.signIn(userService.formUser(email,password,name,lastname,
                                                                  surname,address,phone,"user"));
        } catch (FormUserException e) {
            model.addAttribute("messages", e.getExceptionMessage().getMessagesError());
            return "signin";
        } catch (RoleException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        } catch (UserException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }

        if(authUser!=null){
            return "signinsuccess";
        }

        return "signin";
    }
}
