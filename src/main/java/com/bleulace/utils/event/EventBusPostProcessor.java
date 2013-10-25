package com.bleulace.utils.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import com.bleulace.web.annotation.WebProfile;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * adapted from
 * http://pmeade.blogspot.com/2012/06/using-guava-eventbus-with-spring.html
 * 
 * @author Arleigh Dickerson
 */
@WebProfile
@Component
class EventBusPostProcessor implements BeanPostProcessor,
		DestructionAwareBeanPostProcessor
{
	@Autowired
	private EventBus eventBus;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException
	{
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException
	{
		if (hasAnnotatedMethods(bean))
		{
			register(bean);
		}
		return bean;
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName)
			throws BeansException
	{
		if (hasAnnotatedMethods(bean))
		{
			try
			{
				unregister(bean);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private boolean hasAnnotatedMethods(Object bean)
	{
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods)
		{
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations)
			{
				if (annotation.annotationType().equals(Subscribe.class))
				{
					return true;
				}
			}
		}
		return false;
	}

	private void register(Object bean)
	{
		eventBus.register(bean);
	}

	private void unregister(Object bean)
	{
		try
		{
			eventBus.unregister(bean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}