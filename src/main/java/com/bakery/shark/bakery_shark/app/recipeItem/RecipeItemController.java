package com.bakery.shark.bakery_shark.app.recipeItem;


import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.Ingredient;
import com.bakery.shark.bakery_shark.app.model.Recipe;
import com.bakery.shark.bakery_shark.app.model.RecipeItem;
import com.bakery.shark.bakery_shark.app.recipe.JpaRecipeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/recipeItem")
public class RecipeItemController {

    @Autowired
    Validator validator;

    private final Logger logger = LoggerFactory.getLogger(RecipeItemController.class);

    private final JpaRecipeItemService recipeItemService;
    private final JpaRecipeService recipeService;
    private final JpaIngredientService ingredientService;

    @PostMapping("/add")
    public String addNewRecipeItem(@Valid RecipeItem recipeItem, BindingResult result, Model model, HttpServletRequest request) {

        if(result.hasErrors()){
            HttpSession session = request.getSession();
            Long idOfRecipe = (Long) session.getAttribute("idOfRecipe");
            String s = String.valueOf(idOfRecipe);
            return "redirect:/recipeItem/add/"+s;
        }

        long id = recipeItem.getRecipe().getId();
        String name = recipeItem.getIngredients().getName();

        if(recipeItemService.findRecipeItemByIngredients_NameAndRecipe_Id(name, id)!=null){
            RecipeItem existingRecipeItem = recipeItemService.findRecipeItemByIngredients_NameAndRecipe_Id(name, id);
            existingRecipeItem.setIngredientQuantity(existingRecipeItem.getIngredientQuantity()+recipeItem.getIngredientQuantity());
            recipeItemService.updateRecipeItem(existingRecipeItem);
        }else{
            recipeItemService.addRecipeItem(recipeItem);
        }
        model.addAttribute("recipeId", id);
        Recipe certainRecipe = recipeService.getRecipe(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("recipeItem", recipeItem);
        List<RecipeItem> recipeItemsByRecipeId = recipeItemService.getAllRecipeItemsByid(id);
        model.addAttribute("recipeItemsByRecipeId", recipeItemsByRecipeId);
        return "addRecipeItemToRecipeView";
    }

    @RequestMapping("/add/{id}")
    public String recipeItemForm(Model model, @PathVariable long id, HttpServletRequest request) {
        logger.debug("test recipe id sent to controller from addRecipeView: " + id);
        Recipe certainRecipe = recipeService.getRecipe(id).orElseThrow(EntityExistsException::new);
        model.addAttribute("recipeId", id);
        model.addAttribute("recipeByRecipeId", certainRecipe);
        RecipeItem recipeItem = new RecipeItem();
        recipeItem.setRecipe(certainRecipe);
        recipeItem.setIngredientQuantity(0.0);
        model.addAttribute("recipeItem", recipeItem);
        List<RecipeItem> recipeItemsByRecipeId = recipeItemService.getAllRecipeItemsByid(id);
        model.addAttribute("recipeItemsByRecipeId", recipeItemsByRecipeId);

        HttpSession session = request.getSession();
        if(session.getAttribute("idOfRecipe")==null){
            session.setAttribute("idOfRecipe", id);
        }
        return "addRecipeItemToRecipeView";
    }

    @ModelAttribute("ingredients")
    public List<Ingredient> allIngredients() {
        return ingredientService.getAllIngredients();
    }

    @RequestMapping("/delete/{ingredient}/{recipe}")
    public String deleteRecipeItem(@PathVariable(
            value = "ingredient") Long idIngredient,
                                   @PathVariable(value = "recipe") Long idRecipe, Model model) {
        recipeItemService.deleteRecipeItemOfCertainRecipe(idIngredient, idRecipe);
        return "redirect:/recipeItem/add/" + idRecipe;
    }

    @RequestMapping("/edit/{id}")
    public String editRecipeItem(@PathVariable long id, Model model){
        RecipeItem recipeItem = recipeItemService.getRecipeItem(id).orElseThrow(EntityNotFoundException::new);
        RecipeItem recipeItem1 = new RecipeItem();
        recipeItem1.setId(recipeItem.getId());
        recipeItem1.setRecipe(recipeItem.getRecipe());
        recipeItem1.setIngredients(recipeItem.getIngredients());
        recipeItem1.setIngredientQuantity(recipeItem.getIngredientQuantity());
        model.addAttribute("recipeItem", recipeItem1);
        return "recipeItemEditView";
    }

    @PostMapping("/edit")
    public String updateRecipeItem(@Valid RecipeItem recipeItem, BindingResult result){
        if(result.hasErrors()){
            return "recipeItemEditView";
        }
        recipeItemService.updateRecipeItem(recipeItem);
        return "redirect:/recipeItem/add/"+ recipeItem.getRecipe().getId();
    }

}
