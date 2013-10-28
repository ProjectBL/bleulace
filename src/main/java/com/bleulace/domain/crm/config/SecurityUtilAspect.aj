package com.bleulace.domain.crm.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

aspect SecurityUtilAspect
{
	@Autowired(required = false)
	private SecurityContext ctx;

	Subject around() : execution(public Subject SecurityUtils.getSubject())
	{
		return ctx == null ? proceed() : ctx.getSubject();
	}
}