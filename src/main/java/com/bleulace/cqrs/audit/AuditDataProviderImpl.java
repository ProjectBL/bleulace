package com.bleulace.cqrs.audit;

import java.util.Map;

import org.axonframework.auditing.AuditDataProvider;
import org.axonframework.commandhandling.CommandMessage;
import org.springframework.stereotype.Component;

@Component("auditDataProviderImpl")
public class AuditDataProviderImpl implements AuditDataProvider
{
	@Override
	public Map<String, Object> provideAuditDataFor(CommandMessage<?> command)
	{
		return command.getMetaData();
	}
}