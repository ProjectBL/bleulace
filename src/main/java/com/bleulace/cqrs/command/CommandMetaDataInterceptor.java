package com.bleulace.cqrs.command;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

import com.bleulace.cqrs.ShiroMetaData;

public class CommandMetaDataInterceptor implements CommandDispatchInterceptor
{
	@Override
	public CommandMessage<?> handle(CommandMessage<?> commandMessage)
	{
		try
		{
			commandMessage = commandMessage.andMetaData(ShiroMetaData.get());
		}
		catch (UnavailableSecurityManagerException e)
		{
		}
		return commandMessage;
	}
}