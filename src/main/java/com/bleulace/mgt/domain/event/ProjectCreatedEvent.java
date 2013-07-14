package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ProjectCreatedEvent
{
	private static final long serialVersionUID = -3632936699480409384L;

	private String name;

	private String creatorId;
}
