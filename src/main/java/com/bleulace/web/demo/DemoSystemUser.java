package com.bleulace.web.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.web.SystemUser;

@Component
class DemoSystemUser implements SystemUser
{
	private static final String TARGET_ID_KEY = "targetId";

	@Override
	public String getId()
	{
		return (String) SecurityUtils.getSubject().getPrincipal();
	}

	@Override
	@RequiresUser
	public String getTargetId()
	{
		return (String) SecurityUtils.getSubject().getSession()
				.getAttribute(TARGET_ID_KEY);
	}

	@Override
	@RequiresUser
	public void setTargetId(String targetId)
	{
		Assert.notNull(targetId);
		SecurityUtils.getSubject().getSession()
				.setAttribute(TARGET_ID_KEY, targetId);
	}
}