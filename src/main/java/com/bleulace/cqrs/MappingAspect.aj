package com.bleulace.cqrs;

import org.modelmapper.ModelMapper;

import com.bleulace.utils.ctx.SpringApplicationContext;


public aspect MappingAspect
{
	private static final ModelMapper MAPPER = SpringApplicationContext
			.getBean(ModelMapper.class);

	public static <T> T map(Object source, T destination)
	{
		MAPPER.map(source, destination);
		return destination;
	}

	public static <T> T map(Object source, Class<T> destinationType)
	{
		return MAPPER.map(source, destinationType);
	}
}