package com.bleulace.utils.dto;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

public interface DTOConverter<S, T> extends Converter<S, T>
{
	public List<T> convert(Iterable<S> source);
}