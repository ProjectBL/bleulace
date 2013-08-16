package com.bleulace.utils.dto;

import java.util.ArrayList;
import java.util.List;

public class ModelMappingDTOConverter<S, T> implements DTOConverter<S, T>
{
	private Class<T> clazz;

	public ModelMappingDTOConverter(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public T convert(S source)
	{
		return Mapper.map(source, clazz);
	}

	@Override
	public List<T> convert(Iterable<S> source)
	{
		List<T> toList = new ArrayList<T>();
		for (S from : source)
		{
			T to = convert(from);
			if (to != null)
			{
				toList.add(to);
			}
		}
		return toList;
	}
}