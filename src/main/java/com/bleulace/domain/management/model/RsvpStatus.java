package com.bleulace.domain.management.model;

public enum RsvpStatus
{
	PENDING, ACCEPTED, DECLINED;

	public String getStyleName()
	{
		return name().toLowerCase();
	}
}