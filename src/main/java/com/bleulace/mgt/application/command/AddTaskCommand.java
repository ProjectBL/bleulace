package com.bleulace.mgt.application.command;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AddTaskCommand extends AddBundleCommand
{
	public AddTaskCommand(String parentId)
	{
		super(parentId);
	}
}