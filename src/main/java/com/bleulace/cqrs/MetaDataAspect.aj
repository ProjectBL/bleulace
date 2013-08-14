package com.bleulace.cqrs;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.axonframework.auditing.AuditDataProvider;
import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.domain.MetaData;
import org.springframework.core.env.Environment;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.ctx.SpringApplicationContext;

public aspect MetaDataAspect implements AuditDataProvider, CommandDispatchInterceptor
{
	static final String SUBJECT = "subject";
	static final String TIMESTAMP = "timestamp";

	static final String[] FLAGS = { SUBJECT, TIMESTAMP };

	@SuppressAjWarnings
	before(MetaData metaData, String key) : execution(public * MetaData.put(String,..)) 
		&& args(key) 
		&& target(metaData)
	{
		if (Arrays.asList(FLAGS).contains(key))
		{
			throw new IllegalArgumentException(
					"You are using a reserved word as a metadata key. Use something else.");
		}
	}

	public String MetaData.getSubjectId()
	{
		return this.getSubject().getId();
	}

	public Subject MetaData.getSubject()
	{
		return (Subject) this.get(SUBJECT);
	}

	public Date MetaData.getTimestamp()
	{
		return (Date) this.get(TIMESTAMP);
	}

	public Account MetaData.getAccount()
	{
		return this.getSubjectId() == null ? null : EntityManagerReference
				.load(Account.class, this.getSubjectId());
	}

    public CommandMessage<?> handle(CommandMessage<?> commandMessage)
    {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TIMESTAMP, new Date());
		try
		{
			map.put(SUBJECT, SecurityUtils.getSubject());
		}
		catch (UnavailableSecurityManagerException e)
		{
			if (!isTestEnvironment())
			{
				throw e;
			}
		}
		return commandMessage.andMetaData(map);
    }

	public Map<String, Object> provideAuditDataFor(CommandMessage<?> command)
	{
		return command.getMetaData();
	}

	private boolean isTestEnvironment()
	{
		return Arrays.asList(
				SpringApplicationContext.getBean(Environment.class)
						.getActiveProfiles()).contains("test");
	}
}
