package com.bakery.shark.bakery_shark.app.controller;

import com.bakery.shark.bakery_shark.app.model.User;
import com.bakery.shark.bakery_shark.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    Validator validator;

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/createUser")
    public String createNewUser(Model model){
        model.addAttribute("user", new User());
        return "createUserView";
    }

    @PostMapping("/createUser")
    public String addNewUser(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return"createUserView";
        }
        userService.addNewUserByAdmin(user);
        model.addAttribute("resultOfAdding", "You've add new user");
        return "redirect:/admin/createUser";
    }

    @GetMapping("/deactivateUser/{id}")
    public String deactivateUser(@PathVariable long id){
        User user = userService.findByUserId(id).orElseThrow(EntityNotFoundException::new);
        user.setEnabled(false);
        userService.updateUser(user);
        return "redirect:/bakery/dashboard";
    }

    @GetMapping("/activateUser/{id}")
    public String activateUser(@PathVariable long id){
        User user = userService.findByUserId(id).orElseThrow(EntityNotFoundException::new);
        user.setEnabled(true);
        userService.updateUser(user);
        return "redirect:/bakery/dashboard";
    }
}
