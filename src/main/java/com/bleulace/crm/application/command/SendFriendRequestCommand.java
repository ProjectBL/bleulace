package com.bleulace.crm.application.command;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault = false)
public class SendFriendRequestCommand extends FriendRequestCommand
{
	public SendFriendRequestCommand(String initiatorId, String recipientId)
	{
		super(initiatorId, recipientId);
	}
}
