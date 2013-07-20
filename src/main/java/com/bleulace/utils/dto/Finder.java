package com.bleulace.utils.dto;

import java.util.List;

public interface Finder<T>
{
	public T findById(String id);

	public List<T> findAll();
}