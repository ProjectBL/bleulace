package com.bleulace.utils.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyDAO<T, ID extends Serializable> extends
		Repository<T, ID>
{
	T findOne(ID id);

	boolean exists(ID id);

	long count();

	List<T> findAll();
}