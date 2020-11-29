package com.bakery.shark.bakery_shark.app.ingredient;


import com.bakery.shark.bakery_shark.app.model.Ingredient;
import com.bakery.shark.bakery_shark.app.recipeItem.RecipeItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class JpaIngredientService implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeItemRepository recipeItemRepository;
    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> getIngredient(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        long id = ingredient.getId();
        recipeItemRepository.deleteRecipeItemByIngredients_Id(id);
        ingredientRepository.delete(ingredient);
    }

    @Override
    public void updateIngredient(Ingredient ingredient){
        ingredientRepository.save(ingredient);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getZeroQuantityIngredients(){
        List<Ingredient> ingredientsWithAlertQuantity = ingredientRepository.findAll();
        List<Ingredient> zeroIngredients = ingredientsWithAlertQuantity.stream()
                .filter(ingredient -> ingredient.getLitr() == 0.0 && ingredient.getQuantity() == 0.0 && ingredient.getWeight() == 0)
                .collect(Collectors.toList());
        return  zeroIngredients;
    }

    public List<Ingredient> getAllWithMinimumQuantityIngredients(){
        List<Ingredient> ingredientsWithAlertQuantity = ingredientRepository.findAll();
        List<Ingredient> almostEmptyIngredientList = new ArrayList<>();
        for(Ingredient i : ingredientsWithAlertQuantity){
            if(i.getLitr()<5.0 && i.getQuantity()==0.0 && i.getWeight()==0.0){
                almostEmptyIngredientList.add(i);
            }
            if(i.getLitr()==0 && i.getQuantity()<10.0 && i.getWeight()==0){
                almostEmptyIngredientList.add(i);
            }
            if(i.getLitr()==0 && i.getQuantity()==0 && i.getWeight()<5.0){
                almostEmptyIngredientList.add(i);
            }
        }

        return almostEmptyIngredientList.stream().distinct().collect(Collectors.toList());
    }
}
