package grocerystore.Controllers;

import grocerystore.Domain.Entities.User;
import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IListGroceryService;
import grocerystore.Services.Abstract.IOrderService;
import grocerystore.Services.Abstract.IUserService;
import grocerystore.Services.Models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by raxis on 02.01.2017.
 */
@Controller
@SessionAttributes(value = {"user","role","cart"})
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IListGroceryService listGroceryService;
    @Autowired
    private IUserService userService;

    public OrderController(IOrderService orderService,IListGroceryService listGroceryService,IUserService userService){
        this.orderService=orderService;
        this.listGroceryService=listGroceryService;
        this.userService=userService;
    }

    @RequestMapping(value = "OrderList", method = RequestMethod.GET)
    public String list(@ModelAttribute("user") User user, Model model){
        if(user!=null){
            try {
                model.addAttribute("orderlist",orderService.formOrderViewList(user));
                return "orderlist";
            } catch (DAOException e) {
                model.addAttribute("message",e.getMessage());
                return "exception";
            }
        }
        else {
             return "deadend";
        }
    }

    @RequestMapping(value = "OrderList", method = RequestMethod.POST)
    public ModelAndView list(@RequestParam("orderid") String orderid, Model model){
        try {
            orderService.updateOrder(orderid);
            return new ModelAndView("redirect:OrderList");
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }

    @RequestMapping(value = "OrderListAdmin", method = RequestMethod.GET)
    public String listAdmin(Model model){
        try {
            model.addAttribute("orderlist",orderService.formOrderViewListAdmin());
            return "orderlist_admin";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "OrderEdit", method = RequestMethod.GET)
    public String edit(@RequestParam("orderid") String orderid, Model model){
        try {
            model.addAttribute("order",orderService.formOrderView(orderid));
            return "orderedit";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "OrderEdit", method = RequestMethod.POST)
    public String edit(@RequestParam("orderid") String orderid,@RequestParam("statusid") String statusid,Model model){
        try {
            orderService.updateOrderAdmin(orderid,statusid);
            model.addAttribute("orderlist",orderService.formOrderViewListAdmin());
            return "orderlist_admin";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "OrderAdd", method = RequestMethod.GET)
    public String add(@ModelAttribute("user") User user,@ModelAttribute("cart") Cart cart, Model model){
        if((cart!=null)&&(user!=null)){
            model.addAttribute("user",user);
            model.addAttribute("cart",cart);
            model.addAttribute("totalprice",cart.computeTotalPrice().toString());
            return "orderadd";
        }
        else {
            return "index";
        }
    }

    @RequestMapping(value = "OrderAdd", method = RequestMethod.POST)
    public String add(@ModelAttribute("user") User user,@ModelAttribute("cart") Cart cart,
                      @RequestParam("name") String name, @RequestParam("lastname") String lastname,
                      @RequestParam("surname") String surname, @RequestParam("address") String address,
                      @RequestParam("phone") String phone, Model model){
        if((cart!=null)&&(user!=null)){
            try {
                userService.updateUser(user,name,lastname,surname,address,phone);
                listGroceryService.createListGrocery(cart,orderService.createOrder(user,cart));
                cart.clear();
                return "ordersuccess";
            } catch (DAOException e) {
                model.addAttribute("message",e.getMessage());
                return "exception";
            }
        }
        else {
            return "index";
        }
    }
}
