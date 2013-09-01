package com.bleulace.web.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.DependsOn;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@DependsOn("presenterSubscribingListener")
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@UIComponent
public @interface Presenter
{
	String value() default "";

	String[] viewNames();
}