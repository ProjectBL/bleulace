package com.bleulace.cqrs.command;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.axonframework.commandhandling.CommandMessage;

/**
 * @author Arleigh Dickerson
 * 
 */
privileged aspect CommandMessageAspect
{
	private interface Command
	{
	}

	declare parents : com.bleulace..command.*Command implements Command;

	private final String Command.creatorId = SecurityUtils.getSubject().getId();

	public String Command.getCreatorId()
	{
		return creatorId;
	}

	private Subject CommandMessage.subject;

	before() : execution(CommandMessage+.new(..))
	{
		CommandMessage<?> message = (CommandMessage<?>) thisJoinPoint.getThis();
		message.subject = SecurityUtils.getSubject();
	}

	public Subject CommandMessage.getSubject()
	{
		return subject;
	}
}