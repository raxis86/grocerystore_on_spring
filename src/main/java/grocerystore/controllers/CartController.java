package grocerystore.Controllers;

import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.ICartService;
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
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private ICartService cartService;

    public CartController(ICartService cartService){
        this.cartService=cartService;
    }

    @ModelAttribute("cart")
    public Cart populateCart()
    {
        return new Cart();
    }

    @RequestMapping(value = "CartList", method = RequestMethod.GET)
    public String list(@ModelAttribute("cart") Cart cart, Model model){
        if(cart!=null){
            model.addAttribute("cart",cart);
            model.addAttribute("totalprice",cart.computeTotalPrice().toString());
            return "cart";
        }
        else {
            return "index";
        }
    }

    @RequestMapping(value = "CartAdd", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("cart") Cart cart, @RequestParam("groceryid") String groceryid, Model model){
/*        if(cart==null){
            cart=new Cart();
            model.addAttribute("cart",cart);
        }*/
        try {
            cartService.addToCart(cart,groceryid);
            return new ModelAndView("redirect:GroceryList");
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }

    @RequestMapping(value = "CartRemove", method = RequestMethod.POST)
    public ModelAndView remove(@ModelAttribute("cart") Cart cart, @RequestParam("groceryid") String groceryid, Model model){
        try {
            cartService.removeFromCart(cart,groceryid);
            return new ModelAndView("redirect:CartList");
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }

}
