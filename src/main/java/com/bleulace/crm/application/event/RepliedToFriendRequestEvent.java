package com.bleulace.crm.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class RepliedToFriendRequestEvent extends FriendRequestEvent
{
	private static final long serialVersionUID = -1116473467374406157L;

	private boolean accepted;
}