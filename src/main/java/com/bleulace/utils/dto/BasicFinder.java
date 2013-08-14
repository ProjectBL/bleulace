package com.bleulace.utils.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.bleulace.jpa.EntityManagerReference;

@Configurable
public class BasicFinder<S extends Persistable<String>, T> implements Finder<T>
{
	private final DTOConverter<S, T> converter;

	private final JpaRepository<S, String> repository;

	public BasicFinder(Class<S> sourceClass, Class<T> dtoClass)
	{
		this.converter = new ModelMappingDTOConverter<S, T>(dtoClass);
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

	protected DTOConverter<S, T> getConverter()
	{
		return converter;
	}
}
