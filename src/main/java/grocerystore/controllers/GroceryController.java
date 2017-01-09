package grocerystore.Controllers;

import grocerystore.Domain.Exceptions.DAOException;
import grocerystore.Services.Abstract.IGroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by raxis on 31.12.2016.
 */
@Controller
public class GroceryController {
    private static final Logger logger = LoggerFactory.getLogger(GroceryController.class);

    @Autowired
    private IGroceryService groceryService;

    public GroceryController(IGroceryService groceryService){
        this.groceryService=groceryService;
    }


    @RequestMapping(value = "GroceryList", method = RequestMethod.GET)
    public String list(Model model){
        try {
            model.addAttribute("groceryList",groceryService.getGroceryList());
            return "grocerylist";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryListAdmin", method = RequestMethod.GET)
    public String listAdmin(Model model){
        try {
            model.addAttribute("groceryList",groceryService.getGroceryList());
            return "grocerylist_admin";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryEdit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam("groceryid") String groceryid,
                             @RequestParam("name") String name, @RequestParam("price") String price,
                             @RequestParam("quantity") String quantity, Model model){
        try {
            groceryService.groceryUpdate(groceryid,name,price,quantity);
            return new ModelAndView("redirect:GroceryListAdmin");
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }

    @RequestMapping(value = "GroceryEdit", method = RequestMethod.GET)
    public String edit(@RequestParam("groceryid") String groceryid,Model model){
        try {
            model.addAttribute("grocery",groceryService.getGrocery(groceryid));
            return "groceryedit_admin";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryDel", method = RequestMethod.GET)
    public String deleteGet(@RequestParam("groceryid") String groceryid, Model model){
        try {
            model.addAttribute("grocery",groceryService.getGrocery(groceryid));
            return "grocerydel_admin";
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryDel", method = RequestMethod.POST)
    public ModelAndView deletePost(@RequestParam("groceryid") String groceryid, Model model){
        try {
            groceryService.groceryDelete(groceryid);
            return new ModelAndView("redirect:GroceryListAdmin");
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }

    @RequestMapping(value = "GroceryAdd", method = RequestMethod.GET)
    public String add(){
        return "groceryadd";
    }

    @RequestMapping(value = "GroceryAdd", method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("name") String name, @RequestParam("price") String price,
                      @RequestParam("quantity") String quantity, Model model){
        try {
            groceryService.groceryCreate(name,price,quantity);
            return new ModelAndView("redirect:GroceryListAdmin");
        } catch (DAOException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }
}
