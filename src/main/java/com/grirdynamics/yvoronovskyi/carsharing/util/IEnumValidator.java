package com.grirdynamics.yvoronovskyi.carsharing.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface IEnumValidator {
    Class<? extends Enum<?>> enumClass();

    String message() default "must be any of enum {enum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
