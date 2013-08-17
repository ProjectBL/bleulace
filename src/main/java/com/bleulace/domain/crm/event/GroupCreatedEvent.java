package com.bleulace.domain.crm.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class GroupCreatedEvent
{
	private String title;
}