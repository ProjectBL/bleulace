package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;

import com.bleulace.utils.ctx.SpringApplicationContext;

public class Mapper
{
	private static final ModelMapper DEFAULT_MAPPER = SpringApplicationContext
			.getBean(ModelMapper.class);

	public static <T> T map(Object source, T destination)
	{
		DEFAULT_MAPPER.map(source, destination);
		return destination;
	}

	public static <T> T map(Object source, Class<T> destinationType)
	{
		return DEFAULT_MAPPER.map(source, destinationType);
	}

	public ModelMapper getMapper(Class<?> clazz)
	{
		return DEFAULT_MAPPER;
	}
}