package com.bleulace.jpa;

import java.util.List;

import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyDAO<T extends Persistable<String>> extends
		Repository<T, String>
{
	T findOne(String id);

	boolean exists(String id);

	long count();

	List<T> findAll();

	public List<T> findAll(Iterable<String> ids);
}