package com.jmurilloc.pfc.scouts.vaidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByUsernameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsProductByName {
    String message() default "ya existe en la base de datos!, escoja otro nombre para el producto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
