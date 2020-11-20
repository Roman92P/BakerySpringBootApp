package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityNotFoundException;

public class IngredientConverter implements Converter<String, Ingredient> {

    @Autowired
    private JpaIngredientService ingredientService;

    @Override
    public Ingredient convert(String source) {
        return ingredientService.getIngredient(Long.parseLong(source)).orElseThrow(EntityNotFoundException::new);
    }
}
