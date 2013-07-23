package com.bleulace.cqrs;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroMetaData
{
	private ShiroMetaData()
	{
	}

	public static Map<String, ?> get()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			Subject subject = SecurityUtils.getSubject();

			map.put("subjectId", subject.getId());

			Session session = subject.getSession();
			map.put("host", session.getHost());
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
