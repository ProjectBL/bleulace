package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GroupJoinedEvent
{
	private static final long serialVersionUID = 1774676694112083312L;

	private String groupId;

	private String accountId;

	public GroupJoinedEvent(String groupId, String accountId)
	{
		this.groupId = groupId;
		this.accountId = accountId;
	}

	public GroupJoinedEvent()
	{
	}
}