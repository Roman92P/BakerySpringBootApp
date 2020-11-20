package com.bakery.shark.bakery_shark.app.validation;


import com.bakery.shark.bakery_shark.app.model.Ingredient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IngredientValidator implements ConstraintValidator<IngredientQuantity, Ingredient> {
    @Override
    public void initialize(IngredientQuantity constraintAnnotation) {

    }

    @Override
    public boolean isValid(Ingredient ingredient, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid=true;
        if(ingredient==null){
            isValid=false;
        }
        else if(String.valueOf(ingredient.getLitr()).isEmpty()||String.valueOf(ingredient.getQuantity()).isEmpty()||String.valueOf(ingredient.getWeight()).isEmpty()){
            isValid=false;
        }
        return isValid;
    }
}
