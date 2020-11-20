package com.bakery.shark.bakery_shark.app.recipe;

import com.bakery.shark.bakery_shark.app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {


}
