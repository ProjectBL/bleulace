package com.bleulace.utils.dto;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

public abstract class DTOConverter<S, T> implements Converter<S, T>
{
	private final IterableConverter<S, T> collectionConverter = new IterableConverter<S, T>(
			this);

	public List<T> convert(Iterable<S> source)
	{
		return collectionConverter.convert(source);
	}
}