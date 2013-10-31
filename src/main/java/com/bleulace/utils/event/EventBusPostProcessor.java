package com.bleulace.utils.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * adapted from
 * http://pmeade.blogspot.com/2012/06/using-guava-eventbus-with-spring.html
 * 
 * @author Arleigh Dickerson
 */
public class EventBusPostProcessor implements BeanPostProcessor,
		DestructionAwareBeanPostProcessor
{
	private final EventBus eventBus;

	public EventBusPostProcessor(EventBus eventBus)
	{
		this.eventBus = eventBus;
	}

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

	protected boolean hasAnnotatedMethods(Object bean)
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
		getEventBus().register(bean);
	}

	private void unregister(Object bean)
	{
		try
		{
			getEventBus().unregister(bean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private EventBus getEventBus()
	{
		return eventBus;
	}
}