package com.bleulace.web.demo;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.utils.CallByName;
import com.bleulace.web.SystemUser;

@Component
class DemoSystemUser implements SystemUser
{
	@Autowired
	private CallByName<List<String>> targetIds;

	@Override
	public String getId()
	{
		return (String) SecurityUtils.getSubject().getPrincipal();
	}

	@Override
	@RequiresUser
	public List<String> getTargetIds()
	{
		return targetIds.evaluate();
	}
}