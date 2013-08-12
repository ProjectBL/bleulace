package com.bleulace.domain.crm.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.GroupMembershipAction;

@RooJavaBean
public class GroupMembershipChangedEvent
{
	private String accountId;

	private GroupMembershipAction action;
}
