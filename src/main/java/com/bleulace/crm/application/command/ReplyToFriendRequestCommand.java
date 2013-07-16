package com.bleulace.crm.application.command;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ReplyToFriendRequestCommand extends SendFriendRequestCommand
{
	@NotNull
	private boolean accept = true;

	public ReplyToFriendRequestCommand(String initiatorId, String recipientId)
	{
		super(initiatorId, recipientId);
	}
}
