package com.bakery.shark.bakery_shark.app.ingredient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bakery/ingredients")
public class IngredientRestController {

    private final JpaIngredientService jpaIngredientService;

    public IngredientRestController(JpaIngredientService jpaIngredientService) {
        this.jpaIngredientService = jpaIngredientService;
    }

    @RequestMapping("/zeroQuantity")
    public int numberOfZeroQuantityIngredients(){
        return jpaIngredientService.getZeroQuantityIngredients().size();
    }

    @RequestMapping("/minimumQuantity")
    public int numberOfMinimumQuantityIngredients(){
        return jpaIngredientService.getAllWithMinimumQuantityIngredients().size();
    }

}
