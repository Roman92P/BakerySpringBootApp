package com.bakery.shark.bakery_shark.app.workIngredientQuantity;

import com.bakery.shark.bakery_shark.app.model.WorkIngredientQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkIngredientQuantityRepository extends JpaRepository<WorkIngredientQuantity, Long> {

    WorkIngredientQuantity findByWorkIngredientName(String workIngredientName);

}
