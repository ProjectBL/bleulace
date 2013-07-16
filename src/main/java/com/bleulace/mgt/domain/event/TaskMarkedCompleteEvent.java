package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.event.filter.TaskEvent;

@RooJavaBean
public class TaskMarkedCompleteEvent implements TaskEvent
{
	private static final long serialVersionUID = 1048072637978297426L;

	private String id;
}