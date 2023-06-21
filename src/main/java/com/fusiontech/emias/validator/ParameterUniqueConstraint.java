package com.fusiontech.emias.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ParameterUniqueConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterUniqueConstraint {
    String message() default "Parameter must be unique in rule";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
