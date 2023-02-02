package com.grirdynamics.yvoronovskyi.carsharing.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LicensePlateValidator implements ConstraintValidator<ILicensePlateValidator, String> {

    @Override
    public boolean isValid(String licencePlate, ConstraintValidatorContext cxt) {
        return licencePlate != null && licencePlate.matches("^((([A-Z]{2}[0-9]{4}[A-Z]{2})))$");
    }
}

