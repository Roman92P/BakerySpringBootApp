package com.bakery.shark.bakery_shark.app.workIngredientQuantity;



import com.bakery.shark.bakery_shark.app.model.WorkIngredientQuantity;

import java.util.List;
import java.util.Optional;

public interface WorkIngredientQuantityService {
    List<WorkIngredientQuantity> getAllWorkIngredients();

    Optional<WorkIngredientQuantity> getWorkIngredient(Long id);

    void deleteWorkIngredient(WorkIngredientQuantity workIngredientQuantity);

    void updateWorkIngredient(WorkIngredientQuantity workIngredientQuantity);

    void addWorkIngredient(WorkIngredientQuantity workIngredientQuantity);
}
