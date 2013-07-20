package com.bleulace.utils.ctx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

/**
 * This is a thread-safe way to get a reference to the application context
 * wherever one pleases.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configurable(preConstruction = true)
public class SpringApplicationContext
{
	@Autowired
	private ApplicationContext ctx;

	private SpringApplicationContext()
	{
	}

	/**
	 * 
	 * @return application context
	 */
	public static ApplicationContext get()
	{
		return new SpringApplicationContext().ctx;
	}

	public static Object getBean(String name)
	{
		return get().getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType)
	{
		return get().getBean(requiredType);
	}

	public static <T> T getBean(Class<T> requiredType, String name)
	{
		return get().getBean(name, requiredType);
	}
}