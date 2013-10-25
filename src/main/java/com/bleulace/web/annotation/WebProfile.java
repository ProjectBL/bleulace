package com.bleulace.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

import com.bleulace.utils.SystemProfiles;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
public @interface WebProfile
{
}