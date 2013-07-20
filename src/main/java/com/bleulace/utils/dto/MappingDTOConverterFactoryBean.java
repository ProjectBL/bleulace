package com.bleulace.utils.dto;

import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class MappingDTOConverterFactoryBean<S, T> implements
		FactoryBean<MappingDTOConverter<S, T>>
{
	public MappingDTOConverterFactoryBean()
	{
	}

	private Class<S> sourceClass;

	private Class<T> dtoClass;

	private PropertyMap<S, T> mapping = null;

	@Override
	public MappingDTOConverter<S, T> getObject() throws Exception
	{
		MappingDTOConverter<S, T> converter = new MappingDTOConverter<S, T>(
				sourceClass, dtoClass);
		if (mapping != null)
		{
			converter.getMapper().addMappings(mapping);
		}
		return converter;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<MappingDTOConverter> getObjectType()
	{
		return MappingDTOConverter.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}
}