package com.bleulace.mgt.domain.event;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class EventCreatedEvent extends ProjectCreatedEvent
{
	private static final long serialVersionUID = -2228933387504167888L;

	private Date start;

	private Date end;

	private String location;
}