package com.bakery.shark.bakery_shark.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotZeroDoubleValidatorImpl implements ConstraintValidator<NotZeroDoubleValidator, Double> {

    @Override
    public void initialize(NotZeroDoubleValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Double value, final ConstraintValidatorContext context) {
//        return Double.compare(value, 0) != 0;
        boolean isValid;
        if(value<0){
            return false;
        }
        try {
            isValid = Double.compare(value, 0) != 0;
        } catch (NullPointerException e) {
            isValid = false;
        }
        return isValid;
    }
}
