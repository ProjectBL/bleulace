package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.ManagementAssignment;

@RooJavaBean
public class ManagerAssignedEvent extends
		AssignmentEvent<ManagementAssignment>
{
	private static final long serialVersionUID = 7289677836103776290L;
}