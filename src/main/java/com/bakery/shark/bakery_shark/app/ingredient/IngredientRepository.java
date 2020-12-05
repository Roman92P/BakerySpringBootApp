package com.bakery.shark.bakery_shark.app.ingredient;

import com.bakery.shark.bakery_shark.app.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    @Query(value = "SELECT COUNT(id) FROM ingredients where liters=0 AND quantity=0 AND weight=0", nativeQuery = true)
    Integer getNumberOfEmptyIngredients();

    Ingredient findByNameEquals(String name);

}
