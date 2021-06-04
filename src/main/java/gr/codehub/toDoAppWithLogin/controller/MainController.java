package gr.codehub.toDoAppWithLogin.controller;

import gr.codehub.toDoAppWithLogin.model.Item;
import gr.codehub.toDoAppWithLogin.service.ItemService;
import gr.codehub.toDoAppWithLogin.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final SessionService sessionService;
    private final ItemService itemService;

    @GetMapping("/login")
    public String login() {
        boolean loggedIn = sessionService.isUserLoggedIn(SecurityContextHolder.getContext().getAuthentication());
        if (loggedIn) {
            /* The user is logged in */
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @GetMapping("/")
    public String main(Model model) {
        List<Item> allItems = itemService.findAllItems();
        model.addAttribute("items", allItems);
        return "main";
    }

    @PostMapping("/item/add")
    public String addItem(@RequestParam String description) {
        itemService.addItem(description);
        return "redirect:/";
    }

    @PostMapping("/item/delete")
    public String deleteItem(@RequestParam long id) {
        itemService.deleteItem(id);
        return "redirect:/";
    }
}
