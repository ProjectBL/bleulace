package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class TaskMarkedEvent
{
	private String id;

	private boolean complete;
}