package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Configurable
@RooJavaBean
public class MappingDTOConverter<S, T> extends DTOConverter<S, T>
{
	private final Class<S> sourceClass;
	private final Class<T> dtoClass;

	@Autowired
	private ModelMapper mapper;

	public MappingDTOConverter(Class<S> sourceClass, Class<T> dtoClass)
	{
		this.dtoClass = dtoClass;
		this.sourceClass = sourceClass;
	}

	@Override
	public T convert(S source)
	{
		return source == null ? null : mapper.map(source, dtoClass);
	}

	public ModelMapper getMapper()
	{
		return mapper;
	}
}