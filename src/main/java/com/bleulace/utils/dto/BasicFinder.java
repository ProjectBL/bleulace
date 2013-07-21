package com.bleulace.utils.dto;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class BasicFinder<S, T> implements Finder<T>, FactoryBean<Finder<T>>
{
	private Class<S> sourceClass;
	private Class<T> dtoClass;
	private DTOConverter<S, T> converter;

	@PersistenceContext
	private EntityManager em;

	private JpaRepository<S, String> repository;

	public BasicFinder()
	{
	}

	public BasicFinder(Class<S> sourceClass, Class<T> dtoClass)
	{
		this.dtoClass = dtoClass;
		this.sourceClass = sourceClass;
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

	@PostConstruct
	@Override
	public Finder<T> getObject() throws Exception
	{
		if (sourceClass == null || dtoClass == null)
		{
			throw new IllegalStateException();
		}
		if (converter == null)
		{
			converter = new MappingDTOConverter<S, T>(sourceClass, dtoClass);
		}
		repository = new SimpleJpaRepository<S, String>(sourceClass, em);
		return this;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<BasicFinder> getObjectType()
	{
		return BasicFinder.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	public DTOConverter<S, T> getConverter()
	{
		return converter;
	}
}
