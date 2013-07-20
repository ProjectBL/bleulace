package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;

public abstract class DefaultDTOConverter<S, T> implements DTOConverter<S, T>
{
	private final Class<T> dtoClazz;

	private final ModelMapper mapper = new ModelMapper();

	public DefaultDTOConverter(Class<T> dtoClazz)
	{
		this.dtoClazz = dtoClazz;
	}

	@Override
	public T convert(S source)
	{
		return source == null ? null : mapper.map(source, dtoClazz);
	}

	protected ModelMapper getMapper()
	{
		return mapper;
	}
}