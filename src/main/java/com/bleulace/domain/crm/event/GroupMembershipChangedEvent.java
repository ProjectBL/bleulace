package com.bleulace.domain.crm.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.GroupMembershipAction;

@RooJavaBean(settersByDefault = false)
public class GroupMembershipChangedEvent
{
	private String accountId;

	private GroupMembershipAction action;

	public GroupMembershipChangedEvent()
	{
	}

	public GroupMembershipChangedEvent(String accountId,
			GroupMembershipAction action)
	{
		this.accountId = accountId;
		this.action = action;
	}
}
