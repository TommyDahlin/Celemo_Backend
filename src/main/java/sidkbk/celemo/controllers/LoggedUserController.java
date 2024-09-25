package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sidkbk.celemo.security.loggedUsers.ActiveUserStore;

import java.util.Locale;

@RestController
@RequestMapping(value = "/api/logged")
public class LoggedUserController {

    @Autowired
    ActiveUserStore activeUserStore;

    @GetMapping
    public String getLoggedUser(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }
    /*@GetMapping("/1")
    public List<String> getLoggedUser1() {

        return anotherTest.getUsersFromSessionRegistry();
    }*/
}
