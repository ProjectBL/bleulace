package com.bleulace.utils.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

class IterableConverter<S, T> implements Converter<Iterable<S>, List<T>>
{
	private final Converter<S, T> converter;

	public IterableConverter(Converter<S, T> converter)
	{
		Assert.notNull(converter);
		this.converter = converter;
	}

	@Override
	public List<T> convert(Iterable<S> source)
	{
		List<T> toList = new ArrayList<T>();
		for (S from : source)
		{
			T to = converter.convert(from);
			if (to != null)
			{
				toList.add(to);
			}
		}
		return toList;
	}
}