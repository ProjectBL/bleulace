package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.ManagementLevel;

@RooJavaBean
public class ManagerAddedEvent
{
	private static final long serialVersionUID = 5633251025237859886L;

	private String accountId;
	private String targetId;
	private ManagementLevel level;
}