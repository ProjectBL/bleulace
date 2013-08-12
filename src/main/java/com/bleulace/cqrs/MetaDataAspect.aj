package com.bleulace.cqrs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.axonframework.domain.MetaData;

public aspect MetaDataAspect
{
	static final String AGGREGATE_ID = "aggregateId";
	static final String SUBJECT_ID = "subjectId";
	static final String HOST = "host";
	static final String TIMESTAMP = "timestamp";

	public String MetaData.getSubjectId()
	{
		return (String) this.get(SUBJECT_ID);
	}
	
	public String MetaData.getHost()
	{
		return (String) this.get(HOST);
	}

	public Date MetaData.getTimestamp()
	{
		return (Date) this.get(TIMESTAMP);
	}
	
	public String MetaData.getAggregateId()
	{
		return (String) this.get(AGGREGATE_ID);
	}

	public static Map<String, ?> acquireMetaData()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TIMESTAMP, new Date());
		try
		{
			Subject subject = SecurityUtils.getSubject();

			map.put(SUBJECT_ID, subject.getPrincipal());

			Session session = subject.getSession();
			map.put(HOST, session.getHost());
			for (Object key : session.getAttributeKeys())
			{
				map.put(key.toString(), session.getAttribute(key));
			}
		}
		catch (UnavailableSecurityManagerException e)
		{
		}

		return map;
	}
}
