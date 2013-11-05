package com.bleulace.web.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * adapted from
 * http://pmeade.blogspot.com/2012/06/using-guava-eventbus-with-spring.html
 * 
 * @author Arleigh Dickerson
 */
@Component
class EventBusPostProcessor implements BeanPostProcessor,
		DestructionAwareBeanPostProcessor
{
	@Autowired
	private ApplicationContext ctx;

	public EventBusPostProcessor()
	{
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
		return ctx.getBean("uiBus", EventBus.class);
	}

	@Subscribe
	public void subscribe(DeadEvent event)
	{
		Logger.getLogger(getClass()).warn(
				"Received dead event with source class '"
						+ event.getSource().getClass() + "' and event class '"
						+ event.getEvent().getClass() + "'");
	}
}