package com.bleulace.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.reflect.MethodSignature;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

public aspect EventFilteringAspect
{
	pointcut eventHandlerExecution(Object handler, Object event) : 
		execution(@EventHandler * *.*(*)) 
	&& this(handler) 
	&& args(event);

	pointcut filteredMethodExecution() : execution(@Filter * *.*());

	void around(Object handler, Object event) : 
		eventHandlerExecution(handler,event) 
	&&  filteredMethodExecution()
	{
		if (passesFilters(handler, event))
		{
			proceed(handler, event);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean passesFilters(Object handler, Object event)
	{
		for (EventFilterSpecification filter : acquireFilters(handler, event))
		{
			if (event.getClass().isAssignableFrom(filter.candidateClass())
					&& !filter.isSatisfiedBy(event))
			{
				return false;
			}
		}
		return true;
	}

	private List<EventFilterSpecification<?>> acquireFilters(Object handler, Object event)
	{
		List<EventFilterSpecification<?>> filters = new ArrayList<EventFilterSpecification<?>>();
		Class<?> clazz = handler.getClass();
		for (Field field : clazz.getDeclaredFields())
		{
			if (field.getAnnotation(Filter.class) != null)
			{
				EventFilterSpecification<?> filter = (EventFilterSpecification<?>) ReflectionUtils
						.getField(field, handler);
				if (filter != null)
				{
					filters.add(filter);
				}
			}
		}
		for (Method method : clazz.getDeclaredMethods())
		{
			if (AnnotationUtils.findAnnotation(method, Filter.class) != null)
			{
				EventFilterSpecification<?> filter = (EventFilterSpecification<?>) ReflectionUtils
						.invokeMethod(method, handler);
				if (filter != null)
				{
					filters.add(filter);
				}
			}
		}
		return filters;
	}
}