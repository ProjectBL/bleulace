package com.bleulace.mgt.application.command;

public class MarkTaskCompleteCommand
{
	private String taskId;

	public MarkTaskCompleteCommand(String taskId)
	{
		this.taskId = taskId;
	}
}