package com.bleulace.cqrs.command;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.axonframework.commandhandling.CommandMessage;

/**
 * Keeps maven-apt from freaking out when we generate query classes. Maven-apt
 * doesn't like axon's AggregateRoot classes, so we are just marking them for
 * exclusion.
 * 
 * @author Arleigh Dickerson
 * 
 */
privileged aspect CommandMessageAspect
{
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