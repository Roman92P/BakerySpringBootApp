package com.bakery.shark.bakery_shark.app.ingredient;


import com.bakery.shark.bakery_shark.app.model.Ingredient;
import com.bakery.shark.bakery_shark.app.recipeItem.RecipeItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
}
