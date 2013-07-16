package com.bleulace.crm.application.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class FriendRequestSentEvent
{
	private static final long serialVersionUID = 6712254235674374308L;

	private String initiatorId;

	private String acceptorId;

	public String sagaId()
	{
		return initiatorId + ":" + acceptorId;
	}
}
