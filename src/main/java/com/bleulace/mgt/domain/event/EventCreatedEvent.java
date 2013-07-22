package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.utils.jpa.DateWindow;

@RooJavaBean
public class EventCreatedEvent extends ProjectCreatedEvent
{
	private static final long serialVersionUID = -2228933387504167888L;

	private String location;

	private DateWindow window;

	public EventCreatedEvent(DateWindow window)
	{
		this.window = window;
	}
}