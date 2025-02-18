package com.jmurilloc.pfc.scouts.validation;

import com.jmurilloc.pfc.scouts.util.MessageError;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsProductByNameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsProductByName {

    String message() default "ya existe en la base de datos!, escoja otro nombre para el producto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
