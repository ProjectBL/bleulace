package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public abstract class AssignmentEvent<T extends Enum<T>>
{
	private static final long serialVersionUID = 2599220269755614191L;

	private String id;

	private String accountId;

	private T role;
}
