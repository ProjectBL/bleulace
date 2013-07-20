package com.bleulace.crm.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GroupJoinedEvent
{
	private static final long serialVersionUID = 1774676694112083312L;

	private String accountId;

	public GroupJoinedEvent(String accountId)
	{
		this.accountId = accountId;
	}

	public GroupJoinedEvent()
	{
	}
}