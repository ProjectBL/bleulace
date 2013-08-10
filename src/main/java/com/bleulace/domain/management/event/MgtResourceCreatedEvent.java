package com.bleulace.domain.management.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.command.CreateMgtResourceCommand;

@RooJavaBean
public class MgtResourceCreatedEvent extends CreateMgtResourceCommand
{
	private String id;

	private String creatorId;

	private String title;
}