package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class TaskMarkedCompleteEvent
{
	private static final long serialVersionUID = 1048072637978297426L;

	private String id;
}