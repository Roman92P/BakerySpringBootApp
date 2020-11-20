package com.bakery.shark.bakery_shark.app.user;

import com.bakery.shark.bakery_shark.app.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.Validator;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    Validator validator;

    private final UserService userService;

    @GetMapping("/create-user")
    public String createUser(Model model) {
        model.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping("/create-user")
    public String saveNewUser(@ModelAttribute("newUser") @Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "registration";
        }

        else {
            User byUserName = userService.findByUserName(user.getUsername());
            if(byUserName!=null){
                model.addAttribute("messageExist", "User with this user name already exists");
                return "login";
            }
            userService.saveUser(user);
        }
        return "redirect:/login";
    }


}
