package com.bleulace.utils.dto;

import java.io.Serializable;

public interface DTOFactory<T> extends Serializable
{
	public T make();
}
