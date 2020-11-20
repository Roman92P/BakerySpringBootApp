package com.bakery.shark.bakery_shark.app.recipe;



import com.bakery.shark.bakery_shark.app.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    List<Recipe> getAllRecipes();
    void addRecipe(Recipe recipe);
    void updateRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe);
    Optional<Recipe> getRecipe(Long id);

}
