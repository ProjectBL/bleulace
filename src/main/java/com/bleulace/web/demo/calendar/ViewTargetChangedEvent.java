package com.bleulace.web.demo.calendar;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.SystemUser;

public class ViewTargetChangedEvent
{
	private final String userId;

	ViewTargetChangedEvent(SystemUser user)
	{
		this.userId = user.getId();
	}

	public ViewTargetChangedEvent()
	{
		this(SpringApplicationContext.getUser());
	}

	public String getUserId()
	{
		return userId;
	}
}