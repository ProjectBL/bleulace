package com.bleulace.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@WebProfile
@Component
@Scope(value = "ui", proxyMode = ScopedProxyMode.TARGET_CLASS)
public @interface Presenter
{
	String value() default "";
}