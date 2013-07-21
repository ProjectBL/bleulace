package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class GuestRSVPEvent extends GuestInvitedEvent
{
	private static final long serialVersionUID = -8299495186441530675L;

	private boolean accepted;

	public GuestRSVPEvent()
	{
	}

	public GuestRSVPEvent(String id, String guestId, boolean accepted)
	{
		super(id, guestId);
		this.accepted = accepted;
	}
}