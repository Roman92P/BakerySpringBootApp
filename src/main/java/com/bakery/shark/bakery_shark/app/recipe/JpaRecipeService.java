package com.bakery.shark.bakery_shark.app.recipe;

import com.bakery.shark.bakery_shark.app.model.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JpaRecipeService implements RecipeService{

    private final RecipeRepository recipeRepository;
    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    @Override
    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }
}
