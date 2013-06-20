package com.bleulace.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

@Configurable(preConstruction = true)
public class SpringApplicationContext
{
	@Autowired
	private ApplicationContext ctx;

	private SpringApplicationContext()
	{
	}

	public static ApplicationContext get()
	{
		return new SpringApplicationContext().ctx;
	}
}
