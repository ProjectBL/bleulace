package com.bleulace.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.EntityManagerReference;

public class Locator
{
	public static <T extends Persistable<String>> boolean exists(Class<T> clazz)
	{
		return locate(clazz) != null;
	}

	public static <T extends Persistable<String>> T locate(Class<T> clazz)
	{
		List<AbstractResource> results = new SimpleJpaRepository<AbstractResource, String>(
				AbstractResource.class, EntityManagerReference.get()).findAll();
		if (results.isEmpty())
		{
			return null;
		}
		for (AbstractResource result : results)
		{
			if (result.getClass().equals(clazz))
			{
				return (T) result;
			}
		}
		return null;
	}

	public static <T extends Persistable<String>> void remove(Class<T> clazz)
	{
		EntityManagerReference.get().remove(locate(clazz));
		EntityManagerReference.get().flush();
	}

	public static <T extends Persistable<String>> Set<String> ids(Class<T> clazz)
	{
		JpaRepository<T, String> repo = new SimpleJpaRepository<T, String>(
				clazz, EntityManagerReference.get());
		Set<String> results = new HashSet<String>();
		for (T entity : repo.findAll())
		{
			results.add(entity.getId());
		}
		return results;
	}
}