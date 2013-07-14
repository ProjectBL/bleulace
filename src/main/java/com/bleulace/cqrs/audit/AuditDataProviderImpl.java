package com.bleulace.cqrs.audit;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.axonframework.auditing.AuditDataProvider;
import org.axonframework.commandhandling.CommandMessage;
import org.springframework.stereotype.Component;

@Component("auditDataProviderImpl")
public class AuditDataProviderImpl implements AuditDataProvider
{
	@Override
	public Map<String, Object> provideAuditDataFor(CommandMessage<?> command)
	{
		Map<String, Object> data = new HashMap<String, Object>();

		Subject subject = command.getSubject();
		data.put("principal", subject.getPrincipal());

		Session session = subject.getSession();
		data.put("host", session.getHost());
		data.put("startTimestamp", session.getStartTimestamp());
		data.put("lastAccessTime", session.getLastAccessTime());

		for (Object key : session.getAttributeKeys())
		{
			data.put(key.toString(), session.getAttribute(key));
		}
		return data;
	}
}