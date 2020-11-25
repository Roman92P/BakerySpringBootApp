package com.bakery.shark.bakery_shark.app.ingredient;

import com.bakery.shark.bakery_shark.app.model.Ingredient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Controller
@RequestMapping("/ingredient")
@AllArgsConstructor
public class IngredientController {

    @Autowired
    Validator validator;

    private final Logger logger = LoggerFactory.getLogger(IngredientController.class);

    private final JpaIngredientService ingredientService;

    @RequestMapping("/add")
    public String addIngredientForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "addIngredientForm";
    }

    @PostMapping("/add")
    public String addIngredient(@Valid Ingredient ingredient, BindingResult result){
        if(result.hasErrors()){
            return "addIngredientForm";
        }
            ingredientService.addIngredient(ingredient);
            return "redirect:/main";
    }
    @PostMapping("/edit")
    public String editIngredient(@Valid Ingredient ingredient, BindingResult result){
        if(result.hasErrors()){
            return "editIngredientForm";
        }
        ingredientService.updateIngredient(ingredient);
        return "redirect:/main";
    }

    @RequestMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredient(id).orElseThrow(EntityNotFoundException::new);
        ingredientService.deleteIngredient(ingredient);
        return "redirect:/main";
    }

    @RequestMapping("/edit/{id}")
    public String editIngredientForm(@PathVariable long id, Model model) {
        Ingredient certainIngredient = ingredientService.getIngredient(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("ingredient", certainIngredient);
        return "editIngredientForm";
    }

    @RequestMapping("/warehouse/edit")
    public String editWarehouse(Model model) {
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        model.addAttribute("ingredients", allIngredients);
        model.addAttribute("ingredient", new Ingredient());
        return "warehouseEditView";
    }

    @PostMapping("/warehouse/edit")
    public String editWarehouseIngredient(@Valid Ingredient ingredient, BindingResult result){
        if(result.hasErrors()){
            return "warehouseEditView";
        }
        ingredientService.updateIngredient(ingredient);
        return "redirect:/ingredient/warehouse/edit";
    }

}
