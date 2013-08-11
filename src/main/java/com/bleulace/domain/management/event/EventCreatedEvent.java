package com.bleulace.domain.management.event;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class EventCreatedEvent extends ProjectCreatedEvent
{
	private String creatorId;

	private Date start;

	private Date end;
}