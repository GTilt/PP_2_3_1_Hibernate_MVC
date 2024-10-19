package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

import java.util.List;


@Controller
public class HelloController {

    private UserService userService;

    public HelloController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping ("/")
    public String getAllUsers(ModelMap model) {
        List<User> userList;
        userList = userService.getUsers();
        model.addAttribute("users", userList);
        return "index";
    }

    @GetMapping("/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        System.out.println("Successfully added user");
        return "redirect:/";
    }


    @GetMapping("/edit")
    public String editUser(@RequestParam Long id, ModelMap model) {
        User user = userService.getUserById(id);
        if(user != null) {
            model.addAttribute("user", user);
            return "edit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id, ModelMap model) {
        User user = userService.getUserById(id);
        if(user != null) {
            model.addAttribute("user", user);
            return "delete";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user);
        return "redirect:/";
    }


}