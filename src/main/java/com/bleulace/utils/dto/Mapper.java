package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;

import com.bleulace.cqrs.DomainEventPayload;
import com.bleulace.utils.ctx.SpringApplicationContext;

public final class Mapper
{
	public static <T> T map(Object source, T destination)
	{
		getMapper(source.getClass(), destination.getClass()).map(source,
				destination);
		return destination;
	}

	public static <T> T map(Object source, Class<T> destinationType)
	{
		T instance = acquireInstance(destinationType);
		if (instance == null)
		{
			return getMapper(source.getClass(), destinationType).map(source,
					destinationType);
		}
		getMapper(source.getClass(), destinationType).map(source, instance);
		return instance;
	}

	private static <T> T acquireInstance(Class<T> clazz)
	{
		DTOFactory<T> factory = null;
		for (Object bean : SpringApplicationContext.get()
				.getBeansWithAnnotation(Factory.class).values())
		{
			for (Class<?> targetClass : bean.getClass()
					.getAnnotation(Factory.class).makes())
			{
				if (targetClass.equals(clazz))
				{
					if (factory == null)
					{
						factory = (DTOFactory<T>) bean;
					}
					else
					{
						throw new IllegalStateException(
								"Ambiguous DTOFactory configuration.");
					}
				}
			}
		}
		return factory == null ? null : factory.make();
	}

	public static <S, T> ModelMapper getMapper(Class<S> source,
			Class<T> destination)
	{
		ApplicationContext ctx = SpringApplicationContext.get();
		String name = DomainEventPayload.class.isAssignableFrom(destination) ? ModelMappingConfig.COMMAND_EVENT
				: ModelMappingConfig.DEFAULT;
		return ctx.getBean(name, ModelMapper.class);
	}
}