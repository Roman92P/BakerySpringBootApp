package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.model.Recipe;
import com.bakery.shark.bakery_shark.app.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityExistsException;

public class RecipeConverter implements Converter<String, Recipe> {

    @Autowired
    private RecipeRepository recipeRepository;
    @Override
    public Recipe convert(String source) {
        return recipeRepository.findById(Long.parseLong(source)).orElseThrow(EntityExistsException::new);
    }
}
