package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.utils.jpa.DateWindow;

@RooJavaBean(settersByDefault = false)
public class EventRescheduledEvent
{
	private static final long serialVersionUID = 818429591163191885L;

	private DateWindow window;

	public EventRescheduledEvent(DateWindow range)
	{
		this.window = range;
	}
}