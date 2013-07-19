package com.bleulace.mgt.domain;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { FutureRangeValidator.class })
public @interface FutureRange
{
}
