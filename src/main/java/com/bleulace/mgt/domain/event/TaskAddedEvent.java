package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class TaskAddedEvent
{
	private static final long serialVersionUID = -528146470849704979L;

	private String id;

	private String bundleId;

	private String title;

	public TaskAddedEvent()
	{
	}
}