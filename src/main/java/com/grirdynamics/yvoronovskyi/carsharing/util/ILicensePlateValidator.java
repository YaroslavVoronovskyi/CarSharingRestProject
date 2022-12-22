package com.grirdynamics.yvoronovskyi.carsharing.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = LicensePlateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
public @interface ILicensePlateValidator {

    String message() default "Invalid License plate, should have format -> BC1111CB and should have 8 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
