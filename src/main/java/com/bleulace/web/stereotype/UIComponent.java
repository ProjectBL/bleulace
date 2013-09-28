package com.bleulace.web.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Instances of annotated classes will be scoped per User Interface
 * 
 * @author Arleigh Dickerson
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface UIComponent
{
	String value() default "";
}