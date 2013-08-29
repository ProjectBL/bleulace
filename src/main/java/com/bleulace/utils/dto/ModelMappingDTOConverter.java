package com.bleulace.utils.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.bleulace.utils.ctx.SpringApplicationContext;

class ModelMappingDTOConverter<S, T> implements IterableConverter<S, T>
{
	private final Class<S> source;

	private final Class<T> target;

	private final Converter<S, T> assembler;

	public ModelMappingDTOConverter(Class<S> source, Class<T> target)
	{
		this.source = source;
		this.target = target;
		this.assembler = acquireAssembler();
	}

	@Override
	public T convert(S source)
	{
		return source == null ? null : assembler.convert(source);
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

	private Converter<S, T> acquireAssembler()
	{
		for (Object bean : SpringApplicationContext.get()
				.getBeansWithAnnotation(Assembler.class).values())
		{
			if (bean instanceof Converter)
			{
				Assembler annotation = bean.getClass().getAnnotation(
						Assembler.class);
				Class<?> source = annotation.from();
				Class<?> target = annotation.to();
				if (source.equals(this.source) && target.equals(this.target))
				{
					return (Converter<S, T>) bean;
				}
			}
		}
		return new DefaultAssembler<S, T>(target);
	}
}