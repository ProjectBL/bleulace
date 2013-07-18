package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CommentAddedEvent
{
	private static final long serialVersionUID = -706699590921821542L;

	private String id;

	private String accountId;

	private String content;
}
