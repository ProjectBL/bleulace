package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean
public class GuestInvitedEvent
{
	private static final long serialVersionUID = 647203272361240436L;

	private String id;

	private String creatorId;

	private String guestId;

	public GuestInvitedEvent()
	{
	}

	public GuestInvitedEvent(String id, String guestId)
	{
		Assert.noNullElements(new Object[] { id, guestId });
		this.id = id;
		this.guestId = guestId;
	}

	public String getSagaId()
	{
		return id + ":" + guestId;
	}
}
