package com.bleulace.domain.management.event;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class EventCreatedEvent extends ProjectCreatedEvent
{
	private String location;

	private Date start;

	private Date end;
}