package com.bakery.shark.bakery_shark.app.recipe;


import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.Recipe;
import com.bakery.shark.bakery_shark.app.model.RecipeItem;
import com.bakery.shark.bakery_shark.app.recipeItem.JpaRecipeItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    Validator validator;

    private final JpaRecipeService recipeService;
    private final JpaIngredientService ingredientService;
    private final JpaRecipeItemService recipeItemService;

    @RequestMapping("/add")
    public String addRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("recipeItem", new RecipeItem());
        return "addRecipeView";
    }

    @PostMapping("/add")
    public String addRecipe(@Valid Recipe recipe, BindingResult result) {
        Set<ConstraintViolation<Recipe>> constraintViolations = validator.validate(recipe);
        for ( ConstraintViolation<Recipe> recipeConstraintViolation : constraintViolations ){
            if(recipeConstraintViolation.getPropertyPath().toString().equals("name")){
                return "addRecipeView";
            }
        }
        recipeService.addRecipe(recipe);
        return "redirect:/recipe/add";
    }

    @RequestMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable long id){
        Recipe certainRecipe = recipeService.getRecipe(id).orElseThrow(EntityNotFoundException::new);
        recipeService.deleteRecipe(certainRecipe);
        return "redirect:/main";
    }

    @RequestMapping("/edit/{id}")
    public String editRecipe(@PathVariable long id, Model model){
        Recipe recipe = recipeService.getRecipe(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("recipe", recipe);
        return "addRecipeView";
    }

    @ModelAttribute("allRecipes")
        public List<Recipe>allRecipesToForm(){
        return recipeService.getAllRecipes();
    }
}
