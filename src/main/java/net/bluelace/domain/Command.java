package net.bluelace.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.frugalu.api.messaging.MessageBus;
import com.frugalu.api.messaging.command.AsyncCommandEnvelope;
import com.frugalu.api.messaging.command.CommandFactoryImpl;
import com.frugalu.api.messaging.command.ICommandFactory;
import com.frugalu.api.messaging.command.SyncCommandEnvelope;

@Configurable(preConstruction = true)
public class Command
{
	private final ICommandFactory factory;

	@Autowired
	private MessageBus bus;

	private final Object payload;

	public Command(Object payload)
	{
		this.payload = payload;
		this.factory = new CommandFactoryImpl(bus);
	}

	public AsyncCommandEnvelope withoutCallback()
	{
		return factory.with(payload);
	}

	public <T> SyncCommandEnvelope<T> withCallback(Class<T> callbackClazz)
	{
		return factory.with(payload, callbackClazz);
	}
}
