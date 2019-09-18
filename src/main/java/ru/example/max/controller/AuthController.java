package ru.example.max.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.max.model.User;
import ru.example.max.service.UserService;
import ru.example.max.util.UserValidator;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

   /* @GetMapping("/")
    public String main(
            @RequestParam(value = "name", required = false, defaultValue = "user") String name,
            Model model
    ) {
        model.addAttribute("msg", "Hello " + name + "!");
        return "/index";
    }*/

    @GetMapping("/sign_up") ///users/new
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "/auth/sign_up";
    }

    @PostMapping("/sign_up") ///users/new
    public String signUp(
            @ModelAttribute @Valid User user,
            BindingResult result
    ) {
        userValidator.validate(user, result);
        if(result.hasErrors()) {
            return "/auth/sign_up";
        }
        userService.add(user);
        return  "redirect:/users";
    }

    @RequestMapping("/login")
    public String login() {
        return "/auth/sign_in";
    }
}
