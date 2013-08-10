package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.ManagementRole;

@RooJavaBean
public class ManagerAssignedEvent
{
	private String id;

	private String assignerId;

	private String assigneeId;

	private ManagementRole role;
}
