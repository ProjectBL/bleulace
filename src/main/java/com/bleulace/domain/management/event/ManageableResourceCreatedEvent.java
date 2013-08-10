package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.command.CreateManageableResourceCommand;

@RooJavaBean
public class ManageableResourceCreatedEvent extends CreateManageableResourceCommand
{
	private String id;

	private String creatorId;

	private String title;
}