package grocerystore.controllers;

import grocerystore.domain.exceptions.DAOException;
import grocerystore.services.abstracts.IGroceryService;
import grocerystore.services.exceptions.FormGroceryException;
import grocerystore.services.exceptions.GroceryServiceException;
import grocerystore.services.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

import java.util.List;

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
        } catch (GroceryServiceException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryListAdmin", method = RequestMethod.GET)
    public String listAdmin(Model model){
        try {
            model.addAttribute("groceryList",groceryService.getGroceryList());
            return "grocerylist_admin";
        } catch (GroceryServiceException e) {
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
        } catch (GroceryServiceException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        } catch (FormGroceryException e) {
              model.addAttribute("messages", e.getExceptionMessage().getMessagesError());
              model.addAttribute("groceryid",groceryid);
            return new ModelAndView("redirect:GroceryEdit");
        }
    }

    @RequestMapping(value = "GroceryEdit", method = RequestMethod.GET)
    public String edit(@RequestParam("groceryid") String groceryid,
                       @RequestParam(value = "messages", required = false) List<String> messages,
                       Model model){
        try {
            model.addAttribute("grocery",groceryService.getGrocery(groceryid));
            model.addAttribute("messages",messages);
            return "groceryedit_admin";
        } catch (GroceryServiceException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryDel", method = RequestMethod.GET)
    public String deleteGet(@RequestParam("groceryid") String groceryid, Model model){
        try {
            model.addAttribute("grocery",groceryService.getGrocery(groceryid));
            return "grocerydel_admin";
        } catch (GroceryServiceException e) {
            model.addAttribute("message",e.getMessage());
            return "exception";
        }
    }

    @RequestMapping(value = "GroceryDel", method = RequestMethod.POST)
    public ModelAndView deletePost(@RequestParam("groceryid") String groceryid, Model model){
        try {
            groceryService.groceryDelete(groceryid);
            return new ModelAndView("redirect:GroceryListAdmin");
        } catch (GroceryServiceException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        }
    }

    @RequestMapping(value = "GroceryAdd", method = RequestMethod.GET)
    public String add(@RequestParam(value = "messages", required = false) List<String> messages, Model model)
    {
        model.addAttribute("messages",messages);
        return "groceryadd";
    }

    @RequestMapping(value = "GroceryAdd", method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("name") String name,
                            @RequestParam("price") String price,
                            @RequestParam("quantity") String quantity,
                            Model model){
        try {
            groceryService.groceryCreate(name,price,quantity);
            return new ModelAndView("redirect:GroceryListAdmin");
        } catch (GroceryServiceException e) {
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("exception");
        } catch (FormGroceryException e) {
            model.addAttribute("messages", e.getExceptionMessage().getMessagesError());
            return new ModelAndView("redirect:GroceryAdd");
        }
    }
}
