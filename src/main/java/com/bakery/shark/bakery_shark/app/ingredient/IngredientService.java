package com.bakery.shark.bakery_shark.app.ingredient;




import com.bakery.shark.bakery_shark.app.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Optional<Ingredient> getIngredient(Long id);

    void deleteIngredient(Ingredient ingredient);

    void updateIngredient(Ingredient ingredient);

    void addIngredient(Ingredient ingredient);
}
