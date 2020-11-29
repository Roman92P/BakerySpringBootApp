package com.bakery.shark.bakery_shark.app.recipeItem;

import com.bakery.shark.bakery_shark.app.model.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeItemRepository extends JpaRepository<RecipeItem,Long> {

    List<RecipeItem>findAllByRecipe_Id(Long id);

    RecipeItem findRecipeItemByIngredients_NameAndRecipe_Id(String ingredientsName, Long recipeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM recipe_item WHERE ingredients_id=? and recipe_id=?",nativeQuery = true)
    void deleteRecipeItemByIngredientIdAndRecipeId(
            @Param("ingredient_id") Long ingredient,
            @Param("recipe_id") Long recipe
    );

    @Modifying
    @Transactional
    void deleteRecipeItemByIngredients_Id(Long id);


}
