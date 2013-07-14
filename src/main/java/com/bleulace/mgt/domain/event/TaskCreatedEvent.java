package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class TaskCreatedEvent
{
	private static final long serialVersionUID = -528146470849704979L;

	private String title;

	public TaskCreatedEvent(String title)
	{
		this.title = title;
	}
}
