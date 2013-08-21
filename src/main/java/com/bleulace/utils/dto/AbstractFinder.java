package com.bleulace.utils.dto;

import java.util.List;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.bleulace.jpa.EntityManagerReference;

public class AbstractFinder<S extends Persistable<String>, T> implements
		Finder<T>
{
	private transient final IterableConverter<S, T> converter;

	private transient final JpaRepository<S, String> repository;

	public AbstractFinder(Class<S> sourceClass, Class<T> dtoClass)
	{
		this.converter = new ModelMappingDTOConverter<S, T>(sourceClass,
				dtoClass);
		repository = new SimpleJpaRepository<S, String>(sourceClass,
				EntityManagerReference.get());
	}

	@Override
	public T findById(String id)
	{
		return converter.convert(repository.findOne(id));
	}

	@Override
	public List<T> findAll()
	{
		return converter.convert(repository.findAll());
	}

	protected T convert(S source)
	{
		return converter.convert(source);
	}

	protected List<T> convert(Iterable<S> source)
	{
		return converter.convert(source);
	}
}
