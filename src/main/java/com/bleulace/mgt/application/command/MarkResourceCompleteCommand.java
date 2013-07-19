package com.bleulace.mgt.application.command;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean(settersByDefault = false)
public class MarkResourceCompleteCommand
{
	private final String id;

	public MarkResourceCompleteCommand(String id)
	{
		Assert.notNull(id);
		this.id = id;
	}
}