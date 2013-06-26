package com.bleulace.domain.calendar;

public enum ParticipationStatus
{
	PENDING("Pending", "pending"), ACCEPTED("Accepted", "accepted"), DECLINED(
			"Declined", "declined"), HOST("Host", "accepted");

	private final String displayName;

	private final String styleName;

	private ParticipationStatus(String displayName, String styleName)
	{
		this.displayName = displayName;
		this.styleName = styleName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getStyleName()
	{
		return styleName;
	}
}