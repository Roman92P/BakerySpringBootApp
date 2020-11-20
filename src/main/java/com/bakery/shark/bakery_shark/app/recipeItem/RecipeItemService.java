package com.bakery.shark.bakery_shark.app.recipeItem;



import com.bakery.shark.bakery_shark.app.model.RecipeItem;

import java.util.List;
import java.util.Optional;

public interface RecipeItemService {
    List<RecipeItem> getAllRecipeItems();
    Optional<RecipeItem> getRecipeItem(Long id);
    void deleteRecipeItem(RecipeItem recipeItem);
    void addRecipeItem(RecipeItem recipeItem);
    void updateRecipeItem(RecipeItem recipeItem);

}
