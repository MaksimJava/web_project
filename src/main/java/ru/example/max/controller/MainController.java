package ru.example.max.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.max.dao.UserDao;
import ru.example.max.model.User;
import ru.example.max.util.UserValidator;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/")
    public String main(
            @RequestParam(value = "name", required = false, defaultValue = "user") String name,
            Model model
    ) {
        model.addAttribute("msg", "Hello " + name + "!");
        return "/index";
    }

    @GetMapping("/view/{name}")
    public String view(@PathVariable("name") String name, Model model) {
        model.addAttribute("msg", "Hello " + name + "!");
        return "/index";
    }

    @GetMapping("/raw")
    @ResponseBody
    public String raw() {
        return "Raw data";
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute("users", userDao.getAll());
        return "/users";
    }

    @GetMapping("/users/new")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "/sign_up";
    }

    @PostMapping("/users/new")
    public String signUp(
            @ModelAttribute @Valid User user,
            BindingResult result
    ) throws SQLException {
        userValidator.validate(user, result);
        if(result.hasErrors()) {
            return "/sign_up";
        }
        userDao.add(user);
        return  "redirect:/users";
    }

}
