package com.bleulace.utils;

import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.Saga;
import org.springframework.stereotype.Component;

@Component("nullResourceInjector")
public class NullResourceInjector implements ResourceInjector
{
	@Override
	public void injectResources(Saga saga)
	{
	}
}