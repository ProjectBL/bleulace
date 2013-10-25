package com.bleulace.web.demo.feed.spec;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class BasicUsage<A, P> implements Usage<A, P>
{
	private A action;
	private P predicate;
	private BasicMetaData metaData;

	public void valid()
	{
		Assert.notNull(action);
		Assert.notNull(predicate);
		metaData.valid();
	}
}