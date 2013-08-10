package com.bleulace.cqrs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroMetaData
{
	public static final String SUBJECT_ID = "subjectId";
	public static final String HOST = "host";
	public static final String TIMESTAMP = "timestamp";

	private ShiroMetaData()
	{
	}

	public static Map<String, ?> get()
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
