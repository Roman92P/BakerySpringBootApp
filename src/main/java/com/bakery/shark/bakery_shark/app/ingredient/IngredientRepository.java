package com.bakery.shark.bakery_shark.app.ingredient;

import com.bakery.shark.bakery_shark.app.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
