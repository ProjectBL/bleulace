package com.bleulace.crm.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class StatusUpdatePostedEvent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5583261184607923271L;

	private String id;

	private String accountId;

	private String content;
}
