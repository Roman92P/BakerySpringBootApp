package com.bakery.shark.bakery_shark.app.recipeItem;

import com.bakery.shark.bakery_shark.app.model.RecipeItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JpaRecipeItemService implements RecipeItemService {

    private final RecipeItemRepository recipeItemRepository;

    @Override
    public List<RecipeItem> getAllRecipeItems() {
        return recipeItemRepository.findAll();
    }

    @Override
    public Optional<RecipeItem> getRecipeItem(Long id) {
        return recipeItemRepository.findById(id);
    }

    @Override
    public void deleteRecipeItem(RecipeItem recipeItem) {
        recipeItemRepository.delete(recipeItem);
    }

    @Override
    public void addRecipeItem(RecipeItem recipeItem) {
        recipeItemRepository.save(recipeItem);
    }

    @Override
    public void updateRecipeItem(RecipeItem recipeItem) {
        recipeItemRepository.save(recipeItem);
    }

    public List<RecipeItem> getAllRecipeItemsByid(long id) {
        return recipeItemRepository.findAllByRecipe_Id(id);
    }

    public void deleteRecipeItemOfCertainRecipe(Long ingredientId, Long recipeId){
        recipeItemRepository.deleteRecipeItemByIngredientIdAndRecipeId(ingredientId, recipeId);
    }

    public void deleteItem(long ing, long rec){
        recipeItemRepository.deleteRecipeItemByIngredientIdAndRecipeId(ing, rec);
    }

    public RecipeItem findRecipeItemByIngredients_NameAndRecipe_Id(String ingredientName, Long recipeId){
        return recipeItemRepository.findRecipeItemByIngredients_NameAndRecipe_Id(ingredientName, recipeId);
    }

}
