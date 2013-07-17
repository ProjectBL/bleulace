package com.bleulace.crm.application.command;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ReplyToFriendRequestCommand extends FriendRequestCommand
{
	@NotNull
	private boolean accepted = true;

	public ReplyToFriendRequestCommand(String initiatorId, String recipientId)
	{
		super(initiatorId, recipientId);
	}
}