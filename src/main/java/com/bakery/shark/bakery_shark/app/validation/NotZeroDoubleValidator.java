package com.bakery.shark.bakery_shark.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NotZeroDoubleValidatorImpl.class)
public @interface NotZeroDoubleValidator {
    String message() default "Value should be more than 0.0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Field to validate against 0.
     */
    double field() default 0;
}
