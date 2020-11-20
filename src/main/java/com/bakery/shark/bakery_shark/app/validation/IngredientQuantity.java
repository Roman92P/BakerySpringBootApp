package com.bakery.shark.bakery_shark.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientValidator.class)
public @interface IngredientQuantity {
    String message() default "Don't forget to set quantity! It should be possitive!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
