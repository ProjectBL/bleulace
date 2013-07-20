package com.bleulace.mgt.domain.event;

import java.io.Serializable;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public abstract class AssignmentEvent<T extends Enum<T>> implements
		Serializable
{
	private static final long serialVersionUID = 2599220269755614191L;

	public AssignmentEvent()
	{
	}

	public AssignmentEvent(String id, String accountId, T role)
	{
		this.id = id;
		this.accountId = accountId;
		this.role = role;
	}

	private String id;

	private String accountId;

	private T role;
}
