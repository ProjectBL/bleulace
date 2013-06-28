package com.bleulace.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.frugalu.api.messaging.MessageBus;
import com.frugalu.api.messaging.command.AsyncCommandEnvelope;
import com.frugalu.api.messaging.command.SyncCommandEnvelope;

@Configurable(preConstruction = true)
public class CommandFactory
{
	@Autowired
	private MessageBus bus;

	public static <P> AsyncCommandEnvelope<P> make(P payload)
	{
		return new AsyncCommandEnvelope<P>(new CommandFactory().bus, payload);
	}

	public static <T, P> SyncCommandEnvelope<T, P> make(P payload,
			Class<T> callbackClazz)
	{
		return new SyncCommandEnvelope<T, P>(payload, new CommandFactory().bus,
				callbackClazz);
	}
}