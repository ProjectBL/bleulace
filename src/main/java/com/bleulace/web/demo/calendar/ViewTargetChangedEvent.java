package com.bleulace.web.demo.calendar;

public class ViewTargetChangedEvent
{
	private final String userId;

	ViewTargetChangedEvent(String userId)
	{
		this.userId = userId;
	}

	public String getUserId()
	{
		return userId;
	}
}