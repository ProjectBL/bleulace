package com.bleulace.domain.crm.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GroupCreatedEvent
{
	private String id;

	private String title;
}