package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.ManagementLevel;

@RooJavaBean
public class ManagerAssignedEvent
{
	private String assignerId;

	private String assigneeId;

	private ManagementLevel role;
}
