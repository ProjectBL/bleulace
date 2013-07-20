package com.bleulace.crm.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GroupCreatedEvent
{
	private static final long serialVersionUID = -6482579716836818865L;

	private String title;
}
