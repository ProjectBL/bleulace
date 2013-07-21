package com.bleulace.crm.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GroupJoinedFeedPayload extends GroupJoinedEvent
{
	private static final long serialVersionUID = -7591208351100280691L;

	private String id;

	private String title;
}