package com.bleulace.domain.crm.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Size(min = Password.MIN_SIZE, max = Password.MAX_SIZE)
public @interface Password
{
	static int MIN_SIZE = 7;
	static int MAX_SIZE = 30;
}