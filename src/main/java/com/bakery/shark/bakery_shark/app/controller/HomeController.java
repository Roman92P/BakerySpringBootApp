package com.bakery.shark.bakery_shark.app.controller;


import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.Ingredient;
import com.bakery.shark.bakery_shark.app.model.Product;
import com.bakery.shark.bakery_shark.app.model.Recipe;
import com.bakery.shark.bakery_shark.app.model.User;
import com.bakery.shark.bakery_shark.app.product.JpaProductService;
import com.bakery.shark.bakery_shark.app.recipe.JpaRecipeService;
import com.bakery.shark.bakery_shark.app.user.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/main")
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final JpaProductService jpaProductService;
    private final JpaIngredientService ingredientService;
    private final JpaRecipeService recipeService;

    public HomeController(JpaProductService jpaProductService, JpaIngredientService ingredientService, JpaRecipeService recipeService) {
        this.jpaProductService = jpaProductService;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @RequestMapping("")
    public String showMainPage(@AuthenticationPrincipal CurrentUser currentUser, HttpServletRequest request, Model model) {
        User entityUser = currentUser.getUser();
        model.addAttribute("currentUser", entityUser.getFirstName());
        return "index";
    }

    @ModelAttribute (name = "products")
    public List<Product> allExistProducts() {
        return jpaProductService.getAllProducts();
    }

    @ModelAttribute(name = "ingredients")
    public List<Ingredient> allExistIngredients(){
        return ingredientService.getAllIngredients();
    }
    @ModelAttribute(name = "recipes")
    public List<Recipe> allExistRecipes (){
        return recipeService.getAllRecipes();
    }

    @ModelAttribute(name = "zeroQuantityIngredients")
    public List<Ingredient> allZeroIngredients(){
        return ingredientService.getZeroQuantityIngredients();
    }

    @ModelAttribute(name = "almostEmptyIngredients")
    public List<Ingredient> almostEmptyIngredients(){
        return ingredientService.getAllWithMinimumQuantityIngredients();
    }
}
