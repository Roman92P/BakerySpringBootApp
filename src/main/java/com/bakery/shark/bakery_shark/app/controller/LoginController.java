package com.bakery.shark.bakery_shark.app.controller;


import com.bakery.shark.bakery_shark.app.model.Product;
import com.bakery.shark.bakery_shark.app.model.User;
import com.bakery.shark.bakery_shark.app.product.ProductService;
import com.bakery.shark.bakery_shark.app.user.CurrentUser;
import com.bakery.shark.bakery_shark.app.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    private final ProductService productService;

    public LoginController(ProductService productService) {
        this.productService = productService;
    }


    @RequestMapping("")
    public String home() {
        return "home";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        logger.error("Loger działa");
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, CurrentUser currentUser) {
        User user = currentUser.getUser();
        logger.error("Nowy user czy jest aktywowany: " + user.isActive());

        if (user.isActive()) {
            return "home";
        }
        model.addAttribute("noActiveUser", "User is not activated. Check your email!");
        return "login";
    }

    @RequestMapping("/403")
    public String showErrorPage() {
        return "403";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfuly activated");
        } else {
            model.addAttribute("message", "Activation code is no found");
        }
        return "login";
    }

    @RequestMapping("/offer")
    public String getOffer(Model model) {
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("bakeryProducts", allProducts);

        Map<Long, String> allPictures = new HashMap<>();
        for ( Product p : allProducts ) {
            byte[] img = p.getPhoto();
            String image = "";
            if (img != null && img.length > 0) {
                image = Base64.getEncoder().encodeToString(img);
            }
            allPictures.put(p.getId(), image);
        }

        model.addAttribute("images", allPictures);

        return "offerView";
    }

}
