package com.bleulace.mgt.domain.event;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class EventRescheduledEvent
{
	private static final long serialVersionUID = 818429591163191885L;

	private String id;

	private LocalDateTime start;

	private Period length;
}