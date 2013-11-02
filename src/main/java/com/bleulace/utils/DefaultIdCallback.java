package com.bleulace.utils;

public class DefaultIdCallback implements IdCallback
{
	private final String id;

	public DefaultIdCallback(String id)
	{
		this.id = id;
	}

	@Override
	public String evaluate()
	{
		return id;
	}
}
