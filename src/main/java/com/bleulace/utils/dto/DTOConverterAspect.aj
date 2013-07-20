package com.bleulace.utils.dto;

import java.util.List;


aspect DTOConverterAspect
{
	private final IterableConverter<S, T> DTOConverter<S,T>.collectionConverter = new IterableConverter<S, T>(
			this);

	public List<T> DTOConverter<S,T>.convert(Iterable<S> source)
	{
		return this.collectionConverter.convert(source);
	}
}