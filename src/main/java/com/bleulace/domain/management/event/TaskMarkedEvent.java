package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class TaskMarkedEvent
{
	private final String id;

	private final boolean complete;

	public TaskMarkedEvent(String id, boolean complete)
	{
		this.id = id;
		this.complete = complete;
	}
}