package net.bluelace.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.frugalu.api.messaging.MessageBus;
import com.frugalu.api.messaging.command.AsyncCommandEnvelope;
import com.frugalu.api.messaging.command.CommandFactoryImpl;
import com.frugalu.api.messaging.command.ICommandFactory;
import com.frugalu.api.messaging.command.SyncCommandEnvelope;

@Configurable(preConstruction = true)
public class Command<P>
{
	private final ICommandFactory factory;

	@Autowired
	private transient MessageBus bus;

	private final P payload;

	public Command(P payload)
	{
		this.payload = payload;
		this.factory = new CommandFactoryImpl(bus);
	}

	public static <P> Command<P> make(P payload)
	{
		return new Command<P>(payload);
	}

	public AsyncCommandEnvelope<P> withoutCallback()
	{
		return factory.with(payload);
	}

	public <T> SyncCommandEnvelope<T, P> withCallback(Class<T> callbackClazz)
	{
		return factory.with(payload, callbackClazz);
	}
}
