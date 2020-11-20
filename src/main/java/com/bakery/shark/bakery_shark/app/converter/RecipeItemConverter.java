package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.model.RecipeItem;
import com.bakery.shark.bakery_shark.app.recipeItem.JpaRecipeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityExistsException;

public class RecipeItemConverter implements Converter<String, RecipeItem> {

    @Autowired
    JpaRecipeItemService recipeItemService;

    @Override
    public RecipeItem convert(String source) {
        return recipeItemService.getRecipeItem(Long.parseLong(source)).orElseThrow(EntityExistsException::new);
    }
}
