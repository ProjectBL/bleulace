package com.bleulace.cqrs.audit;

import java.util.List;
import java.util.logging.Logger;

import org.axonframework.auditing.AuditLogger;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.domain.EventMessage;
import org.springframework.stereotype.Component;

@Component("auditLoggerImpl")
@SuppressWarnings("rawtypes")
public class AuditLoggerImpl implements AuditLogger
{
	private static final Logger LOG = Logger.getLogger("AuditLogger");

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
		LOG.warning("Command of type " + message.getCommandName()
				+ " failed with cause: " + failureCause.toString() + ".");
		failureCause.printStackTrace();
	}

}
