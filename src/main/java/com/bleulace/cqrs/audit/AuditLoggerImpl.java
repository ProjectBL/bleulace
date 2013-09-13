package com.bleulace.cqrs.audit;

import java.util.List;

import org.apache.log4j.Logger;
import org.axonframework.auditing.AuditLogger;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.domain.EventMessage;
import org.springframework.stereotype.Component;

@Component("auditLoggerImpl")
@SuppressWarnings("rawtypes")
public class AuditLoggerImpl implements AuditLogger
{
	private static final Logger LOG = Logger.getLogger(AuditLogger.class);

	@Override
	public void logSuccessful(Object command, Object returnValue,
			List<EventMessage> events)
	{
		String msgEnd = returnValue == null ? "void" : returnValue.toString();

		CommandMessage<?> message = (CommandMessage<?>) command;
		LOG.info("Command of type " + message.getCommandName()
				+ " carrying payload " + message.getPayload().toString()
				+ " returned " + msgEnd + ".");
	}

	@Override
	public void logFailed(Object command, Throwable failureCause,
			List<EventMessage> events)
	{
		CommandMessage<?> message = (CommandMessage<?>) command;
		LOG.fatal("Command with name " + message.getCommandName()
				+ " failed with cause: " + failureCause + ".");
	}

}
