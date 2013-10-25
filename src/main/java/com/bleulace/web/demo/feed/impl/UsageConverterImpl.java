package com.bleulace.web.demo.feed.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bleulace.web.demo.feed.spec.EntryFactory;
import com.bleulace.web.demo.feed.spec.Usage;
import com.bleulace.web.demo.feed.spec.UsageConverter;
import com.vaadin.ui.Component;

@org.springframework.stereotype.Component
class UsageConverterImpl implements UsageConverter
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	public <A, P> Component convert(Usage<A, P> source)
	{
		return locateFactory(source).make(source);
	}

	private <A, P> EntryFactory<A, P> locateFactory(Usage<A, P> source)
	{
		EntryFactory<A, P> locatedFactory = null;

		for (EntryFactory<?, ?> factory : ctx
				.getBeansOfType(EntryFactory.class).values())
		{
			if (factory.matches(source))
			{
				if (locatedFactory != null)
				{
					throw new IllegalStateException();
				}
				locatedFactory = (EntryFactory<A, P>) factory;
			}
		}

		if (locatedFactory == null)
		{
			throw new IllegalStateException();
		}

		return locatedFactory;
	}
}
