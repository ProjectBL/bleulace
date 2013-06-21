package com.bleulace.domain.calendar;

public enum ParticipationStatus
{
	PENDING("Pending", null), ACCEPTED("Accepted", null), DECLINED("Declined",
			null), RESCHEDULE_REQUESTED("Reschedule requested", null);

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