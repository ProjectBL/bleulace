package com.bleulace.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.bleulace.utils.jpa.EntityManagerReference;

public class Locator
{
	public static <T extends Persistable<String>> boolean exists(Class<T> clazz)
	{
		return locate(clazz) != null;
	}

	public static <T extends Persistable<String>> T locate(Class<T> clazz)
	{
		List<T> obj = new SimpleJpaRepository<T, String>(clazz,
				EntityManagerReference.get()).findAll();
		if (obj.isEmpty())
		{
			return null;
		}
		return obj.get(0);
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