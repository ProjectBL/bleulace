package com.bleulace.mgt.domain.event;

public class GuestAttendingEvent extends GuestInvitedEvent
{
	private static final long serialVersionUID = -8299495186441530675L;

	public GuestAttendingEvent()
	{
	}

	public GuestAttendingEvent(String id, String guestId)
	{
		super(id, guestId);
	}
}