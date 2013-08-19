package com.bleulace.utils.dto;

import java.io.Serializable;
import java.util.List;

public interface Finder<T> extends Serializable
{
	public T findById(String id);

	public List<T> findAll();
}