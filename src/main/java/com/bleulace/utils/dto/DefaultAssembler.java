package com.bleulace.utils.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

class DefaultAssembler<S, T> implements Converter<S, T>
{
	private final Class<T> targetClass;

	DefaultAssembler(Class<T> targetClass)
	{
		Assert.notNull(targetClass);
		this.targetClass = targetClass;
	}

	@Override
	public T convert(S source)
	{
		return Mapper.map(source, targetClass);
	}
}
