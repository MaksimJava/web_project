package ru.example.max.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.example.max.model.User;

import java.util.*;

@Controller
public class MainController {
    @GetMapping("/{name}")
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
    public String getUsers(Model model) {
        Collection<User> users = new ArrayList<>();
        users.add(new User("John", "Smith", "js@test.com"));
        users.add(new User("Mike", "Johnson", "mj@test.com"));

        model.addAttribute("users", users);
        return "/users";
    }

}
