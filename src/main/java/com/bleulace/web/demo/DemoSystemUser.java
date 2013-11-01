package com.bleulace.web.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import com.bleulace.web.SystemUser;

@Component
class DemoSystemUser implements SystemUser
{
	@Override
	public String getId()
	{
		return (String) SecurityUtils.getSubject().getPrincipal();
	}

	@Override
	public String getTarget()
	{
		return (String) getSession().getAttribute("target");
	}

	@Override
	public void setTarget(String target)
	{
		getSession().setAttribute("target", target);
	}

	private Session getSession()
	{
		return SecurityUtils.getSubject().getSession();
	}
}